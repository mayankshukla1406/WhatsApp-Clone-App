package com.example.whatsapp.domain.use_case

import com.example.whatsapp.domain.repository.UserRespository
import javax.inject.Inject

class ChatUseCase @Inject constructor(
    private val userRespository: UserRespository
) {

    fun getAllChats(userId : String) = userRespository.getAllChats(userId)

}