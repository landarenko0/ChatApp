package com.example.chat.domain.usecases

import com.example.chat.domain.entities.Message
import com.example.chat.domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class SubscribeForMessagesUseCase @Inject constructor(
    private val repository: MessageRepository
) {

    operator fun invoke(): Flow<Message> = repository.messages
}