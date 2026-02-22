package com.example.quizapp.ui.quiz

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizapp.ui.auth.AuthViewModel
import kotlinx.coroutines.delay
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    subject: String,
    userEmail: String,
    viewModel: QuizViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel(),
    onNavigateToProfile: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val realUserEmail = authViewModel.getCurrentUserEmail()

    // ⏱️ LÓGICA DO CRONÔMETRO
    // rememberSaveable garante que o tempo não zera se a pessoa virar o telemóvel
    var elapsedTimeInSeconds by rememberSaveable { mutableLongStateOf(0L) }

    // O cronômetro roda apenas se o quiz carregou, tem perguntas e ainda não acabou
    LaunchedEffect(state.isLoading, state.isFinished, state.questions.isNotEmpty()) {
        if (!state.isLoading && !state.isFinished && state.questions.isNotEmpty()) {
            while (true) {
                delay(1000L) // Espera 1 segundo
                elapsedTimeInSeconds++
            }
        }
    }

    // Formata o tempo para o estilo MM:SS
    val minutes = elapsedTimeInSeconds / 60
    val seconds = elapsedTimeInSeconds % 60
    val timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)

    LaunchedEffect(subject) {
        viewModel.loadQuestions(subject)
    }

    // 🎨 DEGRADÊ DO FUNDO
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF3B82F6), Color(0xFF7C3AED))
    )

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { Text("Quiz: $subject", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.Close, contentDescription = "Sair", tint = Color.White)
                    }
                },
                actions = {
                    // Mostramos o cronômetro no topo também!
                    if (!state.isFinished && !state.isLoading) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(end = 16.dp)) {
                            Icon(Icons.Default.Timer, contentDescription = "Tempo", tint = Color.White, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(timeFormatted, color = Color.White, fontWeight = FontWeight.Bold)
                        }
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
            when {
                state.isLoading -> {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.align(Alignment.Center))
                }
                state.isFinished -> {
                    QuizResultScreen(
                        score = state.score,
                        total = state.questions.size,
                        timeFormatted = timeFormatted, // Passamos o tempo final para a tela de resultado
                        onFinish = {
                            viewModel.saveFinalResult(realUserEmail ?: "usuario_offline@teste.com", subject)
                            onNavigateBack()
                        }
                    )
                }
                state.questions.isEmpty() && !state.isLoading -> {
                    EmptyState(onPopulate = { viewModel.addSampleQuestions() })
                }
                else -> {
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
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // BARRA DE PROGRESSO DOURADA
        val progress = (state.currentQuestionIndex + 1).toFloat() / state.questions.size

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = Color(0xFFFFD700),
            trackColor = Color.White.copy(alpha = 0.2f)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Pergunta ${state.currentQuestionIndex + 1} de ${state.questions.size}",
            color = Color.White.copy(alpha = 0.8f),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        // CARTÃO DA PERGUNTA
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f)),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = currentQuestion.text,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    lineHeight = 28.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // OPÇÕES DE RESPOSTA
        currentQuestion.options.forEachIndexed { index, option ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { onAnswerSelected(index) },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f)),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val letter = ('A' + index).toString()
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = letter, color = Color.White, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = option,
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun QuizResultScreen(score: Int, total: Int, timeFormatted: String, onFinish: () -> Unit) {
    val percentage = (score.toFloat() / total * 100).toInt()
    val isApproved = percentage >= 50
    val resultColor = if (isApproved) Color(0xFF4CAF50) else Color(0xFFFF4B4B)
    val message = if (isApproved) "Parabéns, excelente trabalho!" else "Podes melhorar. Tenta novamente!"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.EmojiEvents,
            contentDescription = null,
            tint = if (isApproved) Color(0xFFFFD700) else Color.White.copy(alpha = 0.5f),
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("Quiz Finalizado!", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "$percentage%",
            fontSize = 72.sp,
            color = resultColor,
            fontWeight = FontWeight.Black
        )

        Text(
            text = "Acertaste $score de $total questões",
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ⏱️ Exibição do Tempo Gasto!
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White.copy(alpha = 0.1f))
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Icon(Icons.Default.Timer, contentDescription = "Tempo", tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Tempo Total: $timeFormatted",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = message,
            color = Color.White,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onFinish,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Guardar e Sair", color = Color(0xFF3B82F6), fontSize = 18.sp, fontWeight = FontWeight.Bold)
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
        Icon(Icons.Default.Refresh, contentDescription = null, tint = Color.White, modifier = Modifier.size(64.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text("Nenhuma questão encontrada.", color = Color.White)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(onClick = onPopulate, colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)) {
            Text("Popular Firebase (Debug)")
        }
    }
}