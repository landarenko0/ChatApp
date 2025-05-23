package com.example.chat.domain.usecases

import com.example.chat.domain.repository.MessageRepository
import javax.inject.Inject

internal class CloseConnectionUseCase @Inject constructor(
    private val repository: MessageRepository
) {

    operator fun invoke() = repository.closeConnection()
}