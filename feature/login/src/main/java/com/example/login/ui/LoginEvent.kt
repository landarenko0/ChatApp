package com.example.login.ui

internal sealed interface LoginEvent {

    data object ShowLoginError : LoginEvent

    data object NavigateToChatScreen : LoginEvent
}