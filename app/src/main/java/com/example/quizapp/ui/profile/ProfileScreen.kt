package com.example.quizapp.ui.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizapp.ui.auth.AuthViewModel
import com.example.quizapp.ui.quiz.QuizViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onBack: () -> Unit,
    onLogout: () -> Unit,
    // Os seus ViewModels injetados perfeitamente aqui:
    authViewModel: AuthViewModel = hiltViewModel(),
    quizViewModel: QuizViewModel = hiltViewModel()
) {
    // Pegando o e-mail do usuário logado exatamente como no seu código antigo
    val userEmail = authViewModel.getCurrentUserEmail() ?: "E-mail não disponível"

    // 🎨 DEGRADÊ DO FUNDO (Padrão do App)
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF3B82F6), Color(0xFF7C3AED))
    )

    Scaffold(
        containerColor = Color.Transparent, // Necessário para o degradê fluir
        topBar = {
            TopAppBar(
                title = { Text("Meu Perfil", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundGradient)
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                // --- SEÇÃO DO AVATAR ---
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.15f)) // Vidro translúcido
                        .border(2.dp, Color.White.copy(alpha = 0.3f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(80.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // --- CARTÃO DE INFORMAÇÕES DO USUÁRIO ---
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f))
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Email, contentDescription = null, tint = Color.White.copy(alpha = 0.7f))
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text("E-mail cadastrado", color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp)
                            // Aqui o e-mail real do usuário é exibido
                            Text(userEmail, color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(48.dp))

                // --- BOTÃO DE LOGOUT ---
                Button(
                    onClick = {
                        // Sua lógica antiga preservada: desloga do Firebase e depois muda de tela
                        authViewModel.signOut()
                        onLogout()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4B4B)), // Vermelho/Salmão
                    shape = RoundedCornerShape(16.dp),
                    elevation = ButtonDefaults.buttonElevation(8.dp)
                ) {
                    Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Sair da Conta (Logout)", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // --- BOTÃO DE POPULAR O BANCO DE DADOS (DEBUG) ---
                OutlinedButton(
                    // Sua lógica de enviar questões para a nuvem
                    onClick = { quizViewModel.populateFirebaseDatabase() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Default.CloudUpload, contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Popular Banco (Debug)")
                }

                Spacer(modifier = Modifier.height(32.dp))
                Text("Quiz Master v1.0", color = Color.White.copy(alpha = 0.4f), fontSize = 12.sp)
            }
        }
    }
}