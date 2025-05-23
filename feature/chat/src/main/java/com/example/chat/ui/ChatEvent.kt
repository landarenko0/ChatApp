package com.example.chat.ui

internal sealed interface ChatEvent {

    data object NavigateToLoginScreen : ChatEvent
}