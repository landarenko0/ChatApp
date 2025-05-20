package com.example.chatapp

import com.example.auth.usecases.CheckUserLoggedInUseCase
import javax.inject.Inject

internal class MainController @Inject constructor(
    private val checkUserLoggedInUseCase: CheckUserLoggedInUseCase
) {

    fun getStartDestination(): AppScreens {
        return if (checkUserLoggedInUseCase()) AppScreens.ChatScreen
        else AppScreens.LoginScreen
    }
}