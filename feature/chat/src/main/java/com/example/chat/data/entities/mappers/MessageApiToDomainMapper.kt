package com.example.chat.data.entities.mappers

import com.example.chat.data.entities.MessageApi
import com.example.chat.domain.entities.Message

fun MessageApi.toDomain(): Message = Message(
    userId = this.userId,
    username = this.username,
    message = this.message
)