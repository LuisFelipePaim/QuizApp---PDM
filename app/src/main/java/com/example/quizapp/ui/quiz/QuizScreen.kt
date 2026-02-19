package com.example.quizapp.ui.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizapp.ui.auth.AuthViewModel // <-- IMPORT NOVO AQUI
import com.example.quizapp.ui.quiz.QuizUiState
import com.example.quizapp.ui.quiz.QuizViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    subject: String,
    userEmail: String, // Vamos ignorar esse e-mail falso que vem da MainActivity
    viewModel: QuizViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel(), // <-- TRAZENDO O CÉREBRO DE AUTENTICAÇÃO
    onNavigateToProfile: () -> Unit,
    onNavigateBack: () -> Unit
) {
    // Observa o estado consolidado (MVVM Puro)
    val state by viewModel.uiState.collectAsState()

    // PEGA O SEU E-MAIL VERDADEIRO DO BANCO DE DADOS
    val realUserEmail = authViewModel.getCurrentUserEmail()

    // Carrega as questões ao entrar na tela (Requisito 2)
    LaunchedEffect(subject) {
        viewModel.loadQuestions(subject)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quiz: $subject") },
                actions = {
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Perfil")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                state.isFinished -> {
                    // Requisito 3.2: Exibir desempenho ao final
                    QuizResultScreen(
                        score = state.score,
                        total = state.questions.size,
                        onFinish = {
                            // AGORA SALVAMOS COM O E-MAIL VERDADEIRO!
                            viewModel.saveFinalResult(realUserEmail, subject)
                            onNavigateBack()
                        }
                    )
                }
                state.questions.isEmpty() && !state.isLoading -> {
                    EmptyState(onPopulate = { viewModel.addSampleQuestions() })
                }
                else -> {
                    // Requisito 3.1: Quiz dinâmico e controle de progresso
                    QuizContent(
                        state = state,
                        onAnswerSelected = { index -> viewModel.submitAnswer(index) }
                    )
                }
            }
        }
    }
}

@Composable
fun QuizContent(state: QuizUiState, onAnswerSelected: (Int) -> Unit) {
    val currentQuestion = state.questions[state.currentQuestionIndex]

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Indicador de Progresso
        LinearProgressIndicator(
            progress = { (state.currentQuestionIndex + 1).toFloat() / state.questions.size },
            modifier = Modifier.fillMaxWidth().height(8.dp)
        )

        Text(
            text = "Pergunta ${state.currentQuestionIndex + 1} de ${state.questions.size}",
            modifier = Modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.labelMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Card da Pergunta
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Text(
                text = currentQuestion.text,
                modifier = Modifier.padding(24.dp),
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Opções de Resposta
        currentQuestion.options.forEachIndexed { index, option ->
            Button(
                onClick = { onAnswerSelected(index) },
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = option, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun QuizResultScreen(score: Int, total: Int, onFinish: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Quiz Finalizado!", style = MaterialTheme.typography.displaySmall)
        Spacer(modifier = Modifier.height(16.dp))

        val percentage = (score.toFloat() / total * 100).toInt()
        Text(
            text = "$percentage%",
            style = MaterialTheme.typography.displayLarge,
            color = if (percentage >= 60) Color(0xFF4CAF50) else Color(0xFFF44336),
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Você acertou $score de $total questões",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(onClick = onFinish, modifier = Modifier.fillMaxWidth()) {
            Text("Salvar e Sair")
        }
    }
}

@Composable
fun EmptyState(onPopulate: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(64.dp))
        Text("Nenhuma questão encontrada.")
        Button(onClick = onPopulate) { Text("Popular Firebase (Debug)") }
    }
}