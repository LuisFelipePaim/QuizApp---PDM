package com.example.quizapp.ui.ranking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizapp.data.model.QuizResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankingScreen(
    onNavigateBack: () -> Unit,
    rankingViewModel: RankingViewModel = hiltViewModel()
    // APAGAMOS o historyViewModel que puxava dados locais
) {
    val topScores by rankingViewModel.topScores.collectAsState()
    val availableSubjects by rankingViewModel.availableSubjects.collectAsState()
    val selectedSubject by rankingViewModel.selectedSubject.collectAsState()
    val isLoading by rankingViewModel.isLoading.collectAsState()

    // Manda buscar da nuvem quando a tela abre!
    LaunchedEffect(Unit) {
        rankingViewModel.loadGlobalRanking()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ranking Global 🏆") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Mostra a rodinha enquanto baixa os dados da internet
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                // --- BARRA DE BOTÕES DAS MATÉRIAS ---
                if (availableSubjects.isNotEmpty()) {
                    Text("Selecione a matéria:", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(availableSubjects) { subject ->
                            FilterChip(
                                selected = subject == selectedSubject,
                                onClick = { rankingViewModel.filterBySubject(subject) },
                                label = { Text(subject) }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // --- LISTA DO PÓDIO ---
                if (topScores.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Nenhum resultado encontrado na nuvem.")
                    }
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        itemsIndexed(topScores) { index, result ->
                            RankingItem(position = index + 1, result = result)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RankingItem(position: Int, result: QuizResult) {
    val (medalColor, medalText) = when (position) {
        1 -> Pair(Color(0xFFFFD700), "🥇")
        2 -> Pair(Color(0xFFC0C0C0), "🥈")
        3 -> Pair(Color(0xFFCD7F32), "🥉")
        else -> Pair(MaterialTheme.colorScheme.surfaceVariant, "${position}º")
    }

    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(40.dp).clip(CircleShape).background(medalColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = medalText,
                    fontWeight = FontWeight.Bold,
                    fontSize = if (position <= 3) 20.sp else 16.sp,
                    color = if (position <= 3) Color.Black else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = result.userEmail.substringBefore("@"),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = result.subject,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Text(
                text = "${result.score} pts",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}