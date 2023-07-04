package com.example.whatsapp.domain.use_case

import com.example.whatsapp.domain.model.User
import com.example.whatsapp.domain.repository.ContactsRepository
import com.example.whatsapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class ContactsUseCase @Inject constructor(
    private val contactsRepository: ContactsRepository
) {
    fun getAllWhatsAppContacts(deviceContacts: List<String>): Flow<Resource<List<User>>> {
        return contactsRepository.getAllUsers(deviceContacts)
    }
}