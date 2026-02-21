package com.example.quizapp.ui.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizapp.data.model.QuizResult
import com.example.quizapp.ui.history.HistoryViewModel // Importando seu ViewModel de histórico
import java.text.SimpleDateFormat
import java.util.*

data class SubjectItem(val name: String, val color: Color, val icon: ImageVector)

@Composable
fun SubjectScreen(
    onNavigateToQuiz: (String) -> Unit,
    onNavigateToHistory: () -> Unit,
    historyViewModel: HistoryViewModel = hiltViewModel()
) {
    // Pegando os dados do ViewModel que acabamos de ajustar
    val history by historyViewModel.results.collectAsState()

    val subjects = listOf(
        SubjectItem("Matemática", Color(0xFFFFB300), Icons.Default.Calculate),
        SubjectItem("História", Color(0xFFE91E63), Icons.Default.HistoryEdu),
        SubjectItem("Geografia", Color(0xFF4CAF50), Icons.Default.Public),
        SubjectItem("Biologia", Color(0xFF00BCD4), Icons.Default.Pets),
        SubjectItem("Física", Color(0xFF9C27B0), Icons.Default.Bolt),
        SubjectItem("Química", Color(0xFFFF5722), Icons.Default.Science)
    )

    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF3B82F6), Color(0xFF7C3AED))
    )

    Box(modifier = Modifier.fillMaxSize().background(backgroundGradient)) {
        // Usamos uma LazyColumn principal para a tela toda ser rolável
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "QUIZ MASTER",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(top = 24.dp)
                )
            }

            // SEÇÃO: MATÉRIAS (Grid dentro da Column)
            item {
                Text("Escolha uma Matéria", color = Color.White, fontWeight = FontWeight.Bold)
                // Criamos um grid manual simples para caber na lista
                Column(modifier = Modifier.fillMaxWidth()) {
                    subjects.chunked(2).forEach { pair ->
                        Row(modifier = Modifier.fillMaxWidth()) {
                            pair.forEach { subject ->
                                Box(modifier = Modifier.weight(1f)) {
                                    SubjectCard(subject) { onNavigateToQuiz(subject.name) }
                                }
                            }
                        }
                    }
                }
            }

            // SEÇÃO: ATIVIDADE RECENTE (TOP 5)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Atividade Recente", color = Color.White, fontWeight = FontWeight.Bold)
                    TextButton(onClick = onNavigateToHistory) {
                        Text("Ver Tudo", color = Color.White.copy(alpha = 0.7f))
                    }
                }
            }

            // Mostra apenas os 5 primeiros
            items(history.take(5)) { result ->
                RecentActivityCard(result)
            }

            if (history.isEmpty()) {
                item {
                    Text("Nenhum quiz feito ainda.", color = Color.White.copy(alpha = 0.5f))
                }
            }
        }
    }
}

@Composable
fun SubjectCard(subject: SubjectItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
            .height(100.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(subject.icon, contentDescription = null, tint = subject.color, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(subject.name, color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun RecentActivityCard(result: QuizResult) {
    val date = SimpleDateFormat("dd/MM HH:mm", Locale.getDefault()).format(Date(result.dateTimestamp))

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
                Text(result.subject, color = Color.White, fontWeight = FontWeight.Bold)
                Text(date, color = Color.White.copy(alpha = 0.5f), fontSize = 12.sp)
            }
            Text(
                "${result.score}/${result.totalQuestions}",
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp
            )
        }
    }
}