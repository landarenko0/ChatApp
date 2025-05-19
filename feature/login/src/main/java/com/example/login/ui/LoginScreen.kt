package com.example.login.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun LoginRoot(
    navigateToChatScreen: () -> Unit
) {
    LoginScreen(
        navigateToChatScreen = navigateToChatScreen,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun LoginScreen(
    navigateToChatScreen: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                LoginEvent.NavigateToChatScreen -> navigateToChatScreen()
                LoginEvent.ShowLoginError -> showLoginErrorToast(context)
            }
        }
    }

    Scaffold(
        modifier = modifier
            .clickable(
                interactionSource = null,
                indication = null
            ) {
                keyboardController?.hide()
                focusManager.clearFocus()
            }
    ) { innerPaddings ->
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPaddings)
                .padding(16.dp)
        ) {
            Text(
                text = "Вход",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = uiState.username,
                onValueChange = viewModel::updateUsername,
                label = { Text(text = "Введите имя пользователя") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    keyboardController?.hide()
                    focusManager.clearFocus()

                    viewModel.login()
                },
                enabled = uiState.loginButtonEnabled && !uiState.isLoading,
                modifier = Modifier.fillMaxWidth().height(40.dp)
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                } else {
                    Text(text = "Войти")
                }
            }
        }
    }
}

private fun showLoginErrorToast(context: Context) {
    Toast.makeText(context, "Произошла ошибка. Попробуйте снова", Toast.LENGTH_SHORT).show()
}