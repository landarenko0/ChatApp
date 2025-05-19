package com.example.chatapp

import kotlinx.serialization.Serializable

internal sealed interface AppScreens {

    @Serializable
    data object LoginScreen : AppScreens

    @Serializable
    data object ChatScreen : AppScreens
}