package com.example.chat.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ChatRoot(
    navigateToLoginScreen: () -> Unit
) {
    ChatScreen()
}

@Composable
private fun ChatScreen(
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = hiltViewModel()
) {

}