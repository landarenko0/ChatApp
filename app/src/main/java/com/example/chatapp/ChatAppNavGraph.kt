package com.example.chatapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chat.ui.ChatRoot
import com.example.login.ui.LoginRoot

@Composable
internal fun ChatAppNavGraph(startDestination: AppScreens) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<AppScreens.LoginScreen> {
            LoginRoot(
                navigateToChatScreen = {
                    navController.navigate(AppScreens.ChatScreen) {
                        navController.popBackStack(
                            route = AppScreens.LoginScreen,
                            inclusive = true
                        )
                    }
                }
            )
        }

        composable<AppScreens.ChatScreen> {
            ChatRoot(
                navigateToLoginScreen = {
                    navController.navigate(AppScreens.LoginScreen) {
                        navController.popBackStack(
                            route = AppScreens.ChatScreen,
                            inclusive = true
                        )
                    }
                }
            )
        }
    }
}