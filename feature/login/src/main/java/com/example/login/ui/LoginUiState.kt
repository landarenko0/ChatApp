package com.example.login.ui

internal data class LoginUiState(
    val username: String = "",
    val loginButtonEnabled: Boolean = false,
    val isLoading: Boolean = false
)