package com.example.chat.domain.repository

import com.example.auth.entities.User
import com.example.chat.domain.entities.Message
import kotlinx.coroutines.flow.Flow

internal interface MessageRepository {

    val messages: Flow<Message>

    suspend fun sendMessage(message: String, user: User)

    fun closeConnection()
}