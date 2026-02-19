package com.example.quizapp.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizapp.ui.auth.AuthViewModel
import com.example.quizapp.ui.quiz.QuizViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onBack: () -> Unit,
    onLogout: () -> Unit,
    // Olha eles aqui em cima, no lugar certinho!
    viewModel: AuthViewModel = hiltViewModel(),
    quizViewModel: QuizViewModel = hiltViewModel()
) {
    // Pega o email do usuário logado usando o AuthViewModel
    val userEmail = viewModel.getCurrentUserEmail()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Meu Perfil") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Ícone grande de perfil
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Foto de Perfil",
                modifier = Modifier.size(120.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email do usuário
            Text(
                text = userEmail,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Botão de Sair da Conta (Logout)
            Button(
                onClick = {
                    viewModel.signOut() // Desloga do Firebase
                    onLogout()          // Volta pra tela de Login
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sair da Conta (Logout)")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botão Temporário para enviar as questões para o Firebase
            Button(
                onClick = { quizViewModel.populateFirebaseDatabase() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Popular Banco de Dados no Firebase")
            }
        }
    }
}