package com.example.whatsapp.domain.repository

import com.example.whatsapp.domain.model.ModelChat
import com.example.whatsapp.domain.model.ModelMessage
import com.example.whatsapp.domain.model.User
import com.example.whatsapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserRespository {
    fun getAllContacts(deviceContacts : List<String>) : Flow<Resource<List<User>>>

    fun getAllChats(userId : String) : Flow<Resource<List<ModelChat>>>

    fun getAllMessagesOfChat(chatId : String) : Flow<Resource<List<ModelMessage>>>
    fun sendMessage(chatId: String, messageModel : ModelMessage): Flow<Resource<Boolean>>

}