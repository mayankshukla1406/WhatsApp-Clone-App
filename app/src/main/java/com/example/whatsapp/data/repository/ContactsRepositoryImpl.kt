package com.example.whatsapp.data.repository

import android.util.Log
import com.example.whatsapp.domain.model.User
import com.example.whatsapp.domain.repository.ContactsRepository
import com.example.whatsapp.util.Resource

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ContactsRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
) : ContactsRepository {

    var batchSize = 10
    var whatsAppContacts : MutableList<User> = arrayListOf()

    override fun getAllUsers(deviceContacts: List<String>): Flow<Resource<List<User>>> = channelFlow {
        try {

            trySend(Resource.Loading)
            val batches = deviceContacts.chunked(batchSize)
            for (contact in deviceContacts) {
                val query = firestore.collection("users").whereEqualTo("userNumber",contact)
                query.get().
                addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result) {
                            whatsAppContacts.add(getUserFromDocument(document))
                            trySend(Resource.Success(whatsAppContacts))
                        }
                    }
                }
            }
            awaitClose()
        } catch (e: Exception) {
            trySend(Resource.Error(e.localizedMessage?:"An Error Occurred"))
        }
    }

    private fun getUserFromDocument(document : QueryDocumentSnapshot) : User {
        return User(userNumber = document.getString("userNumber"),
            userName = document.getString("userName"),
            userImage = document.getString("userImage"),
            userStatus = document.getString("userStatus")
        )
    }
}