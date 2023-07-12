package com.example.whatsapp.data.repository

import com.example.whatsapp.data.database.UserDao
import com.example.whatsapp.domain.model.User
import com.example.whatsapp.domain.repository.ContactsRepository
import com.example.whatsapp.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

class ContactsRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val userDao: UserDao
) : ContactsRepository {

    override fun getAllUsers(deviceContacts: List<String>): Flow<Resource<List<User>>> = channelFlow {
        try {
            trySend(Resource.Loading)
            val contacts = userDao.getAllUsers().firstOrNull()
            if(contacts.isNullOrEmpty()) {
                for (contact in deviceContacts) {
                    val query = firestore.collection("users").whereEqualTo("userNumber",contact)
                    query.get().
                    addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            for (document in task.result) {
                                val user = getUserFromDocument(document)
                                CoroutineScope(Dispatchers.IO).launch {
                                    userDao.insertUser(user)
                                    userDao.getAllUsers().collect {
                                        trySend(Resource.Success(it))
                                    }
                                }
                            }
                        }
                    }
                }
                awaitClose()
            } else {
                trySend(Resource.Success(contacts))
            }
        } catch (e: Exception) {
            trySend(Resource.Error(e.localizedMessage?:"An Error Occurred"))
        }
    }

    private fun getUserFromDocument(document : QueryDocumentSnapshot) : User {
        return User(userNumber = document.getString("userNumber"),
            userName = document.getString("userName"),
            userImage = document.getString("userImage"),
            userStatus = document.getString("userStatus"),
            userId = document.id
        )
    }
}