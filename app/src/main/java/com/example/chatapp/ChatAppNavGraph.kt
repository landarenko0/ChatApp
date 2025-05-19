package com.example.chatapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.login.ui.LoginRoot

@Composable
fun ChatAppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreens.LoginScreen
    ) {
        composable<AppScreens.LoginScreen> {
            LoginRoot(
                navigateToChatScreen = { navController.navigate(AppScreens.ChatScreen) }
            )
        }

        composable<AppScreens.ChatScreen> {

        }
    }
}