package com.example.quizapp.ui.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items // Import essencial para o erro sumir
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizapp.data.model.QuizResult
import com.example.quizapp.data.local.SubjectStat
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel()
) {
    // Adicionei ": List<QuizResult>" para o Android Studio não se confundir
    val history: List<QuizResult> by viewModel.historyState.collectAsState()
    val stats: List<SubjectStat> by viewModel.statsState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Meu Desempenho",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // 1. Dashboard (Estatísticas por Matéria)
        if (stats.isNotEmpty()) {
            Text(
                text = "Médias por Matéria",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
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
                contentPadding = PaddingValues(top = 8.dp)
            ) {
                // Como dissemos que history é uma Lista, o 'items' vai funcionar perfeitamente
                items(history) { result ->
                    HistoryItem(result)
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