package com.example.chat.domain.usecases

import com.example.auth.entities.User
import com.example.chat.domain.repository.MessageRepository
import javax.inject.Inject

internal class SendMessageUseCase @Inject constructor(
    private val repository: MessageRepository
) {

    suspend operator fun invoke(message: String, user: User) = repository.sendMessage(message, user)
}