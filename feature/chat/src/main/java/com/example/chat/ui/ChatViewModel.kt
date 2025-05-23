package com.example.chat.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.entities.User
import com.example.auth.usecases.GetSavedUserUseCase
import com.example.chat.domain.usecases.CloseConnectionUseCase
import com.example.chat.domain.usecases.SendMessageUseCase
import com.example.chat.domain.usecases.SubscribeForMessagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ChatViewModel @Inject constructor(
    getSavedUserUseCase: GetSavedUserUseCase,
    private val subscribeForMessagesUseCase: SubscribeForMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val closeConnectionUseCase: CloseConnectionUseCase
) : ViewModel() {

    private var user: User? = null

    private val _event = Channel<ChatEvent>()
    val event = _event.receiveAsFlow()

    init {
        val savedUser = getSavedUserUseCase()

        if (savedUser == null) {
            viewModelScope.launch { _event.send(ChatEvent.NavigateToLoginScreen) }
        } else {
            user = savedUser
        }

        viewModelScope.launch {
            subscribeForMessagesUseCase().collect { message ->
                Log.d("ChatViewModel", "Received: ${message.message}")
            }
        }
        viewModelScope.launch {
            delay(3000)
            sendMessageUseCase("Hello from viewModel!", user!!)
        }
    }

    override fun onCleared() {
        super.onCleared()
        closeConnectionUseCase()
    }
}