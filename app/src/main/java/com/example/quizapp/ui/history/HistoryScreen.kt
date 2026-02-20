package com.example.quizapp.ui.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizapp.data.model.QuizResult
import com.example.quizapp.data.local.SubjectStat
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
    val history: List<QuizResult> by viewModel.historyState.collectAsState()
    val stats: List<SubjectStat> by viewModel.statsState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Meu Desempenho") },
                actions = {
                    // Botão do Ranking (Troféu)
                    IconButton(onClick = onNavigateToRanking) {
                        Text("🏆", fontSize = 24.sp)
                    }
                    // Botão do Perfil
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Meu Perfil",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onStartQuiz,
                icon = { Icon(Icons.Default.PlayArrow, contentDescription = "Jogar") },
                text = { Text("Novo Quiz") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // 1. Dashboard (Estatísticas por Matéria)
            if (stats.isNotEmpty()) {
                Text(
                    text = "Médias por Matéria",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                StatsCards(stats)
                Spacer(modifier = Modifier.height(24.dp))
            }

            // 2. Histórico Detalhado
            Text(
                text = "Histórico de Partidas",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            if (history.isEmpty()) {
                Text(text = "Nenhum quiz realizado ainda.", modifier = Modifier.padding(top = 8.dp))
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(top = 8.dp, bottom = 80.dp) // Espaço para o botão não tampar a lista
                ) {
                    items(history) { result ->
                        HistoryItem(result)
                    }
                }
            }
        }
    }
}

@Composable
fun StatsCards(stats: List<SubjectStat>) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        stats.forEach { stat ->
            Card(modifier = Modifier.weight(1f)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = stat.subject, style = MaterialTheme.typography.labelLarge)
                    Text(
                        text = "%.1f".format(stat.averageScore),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        }
    }
}

@Composable
fun HistoryItem(result: QuizResult) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = result.subject, style = MaterialTheme.typography.titleSmall)
                Text(
                    text = "${result.score}/${result.totalQuestions}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Text(
                text = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date(result.dateTimestamp)),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}