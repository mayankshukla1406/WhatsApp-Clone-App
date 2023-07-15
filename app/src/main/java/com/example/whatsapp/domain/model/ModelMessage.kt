package com.example.whatsapp.domain.model

data class ModelMessage(
    var messageType : String,
    var message : String,
    var messageSender : String,
    var messageReceiver : String
)