package com.example.chat.domain.entities

data class Message(
    val userId: Long,
    val username: String,
    val message: String
)