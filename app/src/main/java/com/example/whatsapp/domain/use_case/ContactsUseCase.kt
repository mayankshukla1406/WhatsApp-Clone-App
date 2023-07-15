package com.example.whatsapp.domain.use_case

import com.example.whatsapp.domain.model.User
import com.example.whatsapp.domain.repository.UserRespository
import com.example.whatsapp.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ContactsUseCase @Inject constructor(
    private val userRespository: UserRespository
) {
    fun getAllWhatsAppContacts(deviceContacts: List<String>): Flow<Resource<List<User>>> {
        return userRespository.getAllContacts(deviceContacts)
    }
}