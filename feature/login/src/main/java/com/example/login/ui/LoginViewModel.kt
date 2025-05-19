package com.example.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.usecases.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = Channel<LoginEvent>()
    val event = _event.receiveAsFlow()

    fun updateUsername(username: String) {
        if (username.length <= 20) {
            _uiState.update {
                it.copy(
                    username = username,
                    loginButtonEnabled = username.isNotEmpty()
                )
            }
        }
    }

    fun login() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val loginResult = loginUseCase(_uiState.value.username)

            if (loginResult.isSuccess) {
                _event.send(LoginEvent.NavigateToChatScreen)
            } else {
                _event.send(LoginEvent.ShowLoginError)
            }

            _uiState.update { it.copy(isLoading = false) }
        }
    }
}