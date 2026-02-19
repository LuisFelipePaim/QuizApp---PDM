package com.example.quizapp.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginScreen(
    onNavigateToHome: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Observa o estado do MVVM
    val state by viewModel.uiState.collectAsState()

    // Se o login for sucesso, navega para a próxima tela
    LaunchedEffect(state) {
        if (state is AuthUiState.Success) {
            onNavigateToHome()
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-mail") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Senha") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (state is AuthUiState.Loading) {
            CircularProgressIndicator()
        } else {
            Button(onClick = { viewModel.performLogin(email, password) }) {
                Text("Entrar")
            }
        }

        if (state is AuthUiState.Error) {
            Text(
                text = (state as AuthUiState.Error).message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}