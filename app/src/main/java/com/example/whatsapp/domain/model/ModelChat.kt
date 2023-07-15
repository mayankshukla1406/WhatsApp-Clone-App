package com.example.whatsapp.domain.model

import java.sql.Timestamp

data class ModelChat(
    var chatId : String,
    var chatParticipants : List<String>,
    var chatName : String,
    var chatImage : String,
    var chatLastMessage : String,
    var chatLastMessageTimestamp: String
)