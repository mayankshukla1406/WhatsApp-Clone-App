package com.example.whatsapp.domain.model

data class ModelMessage(
    var messageType : String,
    var messageData : String,
    var messageSender : String,
    var messageReceiver : String
)