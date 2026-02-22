package com.example.quizapp.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizapp.data.local.SubjectStat
import com.example.quizapp.data.model.QuizResult
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    onNavigateToProfile: () -> Unit,
    onStartQuiz: () -> Unit,
    onNavigateToRanking: () -> Unit,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val history by viewModel.historyState.collectAsState()
    val stats by viewModel.statsState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // 🎨 DEGRADÊ DO FUNDO
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF3B82F6), Color(0xFF7C3AED))
    )

    // Estrutura principal da tela
    Scaffold(
        containerColor = Color.Transparent, // Fundamental para o degradê aparecer!
        topBar = {
            TopAppBar(
                title = { Text("Meu Desempenho", color = Color.White, fontWeight = FontWeight.ExtraBold) },
                actions = {
                    // Ícone do Ranking (Dourado)
                    IconButton(onClick = onNavigateToRanking) {
                        Icon(Icons.Filled.EmojiEvents, contentDescription = "Ranking", tint = Color(0xFFFFD700))
                    }
                    // Ícone do Perfil
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(Icons.Filled.Person, contentDescription = "Perfil", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        floatingActionButton = {
            // Botão "Novo Quiz" chamativo flutuando no canto
            ExtendedFloatingActionButton(
                onClick = onStartQuiz,
                containerColor = Color(0xFFFF4B4B), // Rosa/Vermelho Vibrante
                contentColor = Color.White,
                shape = RoundedCornerShape(24.dp)
            ) {
                Icon(Icons.Filled.PlayArrow, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Novo Quiz", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    ) { padding ->
        // Fundo com o degradê
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundGradient)
                .padding(padding)
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {

                    // SEÇÃO 1: MÉDIAS POR MATÉRIA (Scroll Horizontal)
                    if (stats.isNotEmpty()) {
                        item {
                            Text("Médias por Matéria", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Spacer(modifier = Modifier.height(12.dp))
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                items(stats) { stat ->
                                    StatCard(stat)
                                }
                            }
                        }
                    }

                    // SEÇÃO 2: HISTÓRICO COMPLETO DE PARTIDAS
                    item {
                        Text("Histórico de Partidas", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }

                    items(history) { result ->
                        HistoryItemCard(result)
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    if (history.isEmpty() && !isLoading) {
                        item {
                            Text("Nenhum quiz encontrado. Comece a jogar!", color = Color.White.copy(alpha = 0.7f))
                        }
                    }

                    // Espaço extra no final para o botão flutuante não tapar o último item
                    item { Spacer(modifier = Modifier.height(80.dp)) }
                }
            }
        }
    }
}

// 🃏 CARD DA MÉDIA (Quadrado Transparente)
@Composable
fun StatCard(stat: SubjectStat) {
    Card(
        modifier = Modifier.width(140.dp).height(100.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f))
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(stat.subject, color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))

            // Formata a nota para ter apenas 1 casa decimal (ex: 9.0)
            val formattedAvg = String.format(Locale.US, "%.1f", stat.averageScore)
            Text(formattedAvg, color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 28.sp)
        }
    }
}

// 🃏 CARD DA PARTIDA INDIVIDUAL (Retângulo Transparente)
@Composable
fun HistoryItemCard(result: QuizResult) {
    val date = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date(result.dateTimestamp))

    // Regra visual: Verde se acertou metade ou mais, Vermelho claro se reprovou
    val isApproved = result.score >= (result.totalQuestions / 2.0)
    val scoreColor = if (isApproved) Color(0xFF4CAF50) else Color(0xFFFF8A80)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(result.subject, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(date, color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp)
            }

            Text(
                text = "${result.score}/${result.totalQuestions}",
                color = scoreColor,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp
            )
        }
    }
}