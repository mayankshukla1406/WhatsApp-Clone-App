package com.example.whatsapp.domain.repository

import com.example.whatsapp.domain.model.User
import com.example.whatsapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface ContactsRepository {
    fun getAllUsers(deviceContacts : List<String>) : Flow<Resource<List<User>>>
}