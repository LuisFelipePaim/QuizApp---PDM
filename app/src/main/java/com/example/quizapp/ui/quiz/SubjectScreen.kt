package com.example.quizapp.ui.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.quizapp.ui.history.HistoryViewModel
import java.text.SimpleDateFormat
import java.util.*

data class SubjectItem(val name: String, val color: Color, val icon: ImageVector)

@Composable
fun SubjectScreen(
    onNavigateToQuiz: (String) -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToAllSubjects: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToRanking: () -> Unit,
    onNavigateToDashboard: () -> Unit, // NOVO: Rota para o Dashboard!
    historyViewModel: HistoryViewModel = hiltViewModel()
) {
    val history by historyViewModel.results.collectAsState()

    // Atualiza o histórico sempre que a tela é aberta
    LaunchedEffect(Unit) {
        historyViewModel.loadHistory()
    }

    val allSubjects = listOf(
        SubjectItem("Matemática", Color(0xFFFFB300), Icons.Default.Calculate),
        SubjectItem("História", Color(0xFFE91E63), Icons.Default.HistoryEdu),
        SubjectItem("Geografia", Color(0xFF4CAF50), Icons.Default.Public),
        SubjectItem("Biologia", Color(0xFF00BCD4), Icons.Default.Pets),
        SubjectItem("Física", Color(0xFF9C27B0), Icons.Default.Bolt),
        SubjectItem("Química", Color(0xFFFF5722), Icons.Default.Science)
    )

    // Sorteia 3 matérias aleatórias
    val randomSubjects = remember { allSubjects.shuffled().take(3) }

    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF3B82F6), Color(0xFF7C3AED))
    )

    Box(modifier = Modifier.fillMaxSize().background(backgroundGradient)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // CABEÇALHO COM TÍTULO E ÍCONES
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "QUIZ MASTER",
                        color = Color.White,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    // 🚀 Ícones agrupados: Dashboard, Ranking e Perfil
                    Row {
                        IconButton(onClick = onNavigateToDashboard) {
                            Icon(Icons.Filled.Dashboard, contentDescription = "Painel", tint = Color.White)
                        }
                        IconButton(onClick = onNavigateToRanking) {
                            Icon(Icons.Filled.EmojiEvents, contentDescription = "Ranking", tint = Color(0xFFFFD700))
                        }
                        IconButton(onClick = onNavigateToProfile) {
                            Icon(Icons.Filled.Person, contentDescription = "Perfil", tint = Color.White)
                        }
                    }
                }
            }

            // SEÇÃO: MATÉRIAS ALEATÓRIAS
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Matérias em Destaque", color = Color.White, fontWeight = FontWeight.Bold)
                    TextButton(onClick = onNavigateToAllSubjects) {
                        Text("Ver Todas", color = Color.White.copy(alpha = 0.7f))
                    }
                }
            }

            items(randomSubjects) { subject ->
                SubjectCard(subject) { onNavigateToQuiz(subject.name) }
            }

            // SEÇÃO: ATIVIDADE RECENTE
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Últimos Quizzes", color = Color.White, fontWeight = FontWeight.Bold)
                    TextButton(onClick = onNavigateToHistory) {
                        Text("Ver Histórico", color = Color.White.copy(alpha = 0.7f))
                    }
                }
            }

            items(history.take(3)) { result ->
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
            .fillMaxWidth()
            .height(80.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f))
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(subject.icon, contentDescription = null, tint = subject.color, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(subject.name, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
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