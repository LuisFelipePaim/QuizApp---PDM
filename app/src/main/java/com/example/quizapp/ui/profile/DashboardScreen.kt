package com.example.quizapp.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizapp.ui.auth.AuthViewModel
import com.example.quizapp.ui.history.HistoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateBack: () -> Unit,
    authViewModel: AuthViewModel = hiltViewModel(),
    historyViewModel: HistoryViewModel = hiltViewModel()
) {
    // 1. Pegando o histórico real do usuário logado
    val history by historyViewModel.results.collectAsState()

    // Atualiza o histórico sempre que abrir a tela
    LaunchedEffect(Unit) {
        historyViewModel.loadHistory()
    }

    // 2. Pegando o nome do usuário através do e-mail
    val userEmail = authViewModel.getCurrentUserEmail() ?: "Jogador"
    val userName = userEmail.substringBefore("@").replaceFirstChar { it.uppercase() }

    // --- CÁLCULOS REAIS DAS ESTATÍSTICAS ---
    val quizzesPlayed = history.size
    val totalCorrectAnswers = history.sumOf { it.score }
    val totalQuestionsAnswered = history.sumOf { it.totalQuestions }

    // Precisão: (Acertos / Total) * 100
    val accuracy = if (totalQuestionsAnswered > 0) {
        ((totalCorrectAnswers.toFloat() / totalQuestionsAnswered) * 100).toInt()
    } else 0

    // XP: 50 pontos por cada questão correta
    val totalXp = totalCorrectAnswers * 50

    // Sistema de Níveis: A cada 500 XP sobe 1 nível
    val level = (totalXp / 500) + 1
    val levelTitle = when (level) {
        1 -> "Iniciante"
        2, 3 -> "Aprendiz"
        4, 5 -> "Intermediário"
        6, 7 -> "Avançado"
        8, 9 -> "Especialista"
        else -> "Mestre"
    }

    // Calculando as 3 melhores matérias
    val bestSubjects = history.groupBy { it.subject }
        .map { (subject, quizzes) ->
            val correct = quizzes.sumOf { it.score }
            val total = quizzes.sumOf { it.totalQuestions }
            val avg = if (total > 0) correct.toFloat() / total else 0f
            Pair(subject, avg)
        }
        .sortedByDescending { it.second } // Ordena do maior para o menor
        .take(3) // Pega apenas os 3 primeiros

    // 🎨 DEGRADÊ DO FUNDO (Padrão do App)
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF3B82F6), Color(0xFF7C3AED))
    )

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { Text("Meu Painel", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // --- 1. CABEÇALHO DO JOGADOR ---
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(70.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(40.dp))
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Text("Olá, $userName!", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
                            Text("Nível $level - $levelTitle", color = Color(0xFFFFD700), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }
                }

                // --- 2. ESTATÍSTICAS RÁPIDAS ---
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        StatBox(modifier = Modifier.weight(1f), icon = Icons.Default.Stars, title = "XP Total", value = "$totalXp", color = Color(0xFFFFD700))
                        StatBox(modifier = Modifier.weight(1f), icon = Icons.Default.Gamepad, title = "Partidas", value = "$quizzesPlayed", color = Color(0xFF4CAF50))
                        StatBox(modifier = Modifier.weight(1f), icon = Icons.Default.TrackChanges, title = "Precisão", value = "$accuracy%", color = Color(0xFF00BCD4))
                    }
                }

                // --- 3. DESEMPENHO POR MATÉRIA ---
                item {
                    Text("Melhores Matérias", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(12.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            if (bestSubjects.isEmpty()) {
                                Text("Jogue algumas partidas para ver o seu desempenho aqui!", color = Color.White.copy(alpha = 0.6f))
                            } else {
                                bestSubjects.forEachIndexed { index, subjectData ->
                                    val percent = (subjectData.second * 100).toInt()
                                    SubjectProgressBar(subject = subjectData.first, progress = subjectData.second, progressStr = "$percent%")

                                    // Adiciona um espaço, exceto no último item
                                    if (index < bestSubjects.size - 1) {
                                        Spacer(modifier = Modifier.height(16.dp))
                                    }
                                }
                            }
                        }
                    }
                }

                // --- 4. CONQUISTAS (Badges Dinâmicas) ---
                item {
                    Text("Minhas Conquistas", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(12.dp))

                    // Lógica real para desbloquear
                    val isInvicto = accuracy == 100 && quizzesPlayed >= 1
                    val isVeterano = quizzesPlayed >= 10
                    val isMestre = level >= 5

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        item { BadgeItem(icon = Icons.Default.LocalFireDepartment, title = "Primeiro Passo", isUnlocked = quizzesPlayed > 0) }
                        item { BadgeItem(icon = Icons.Default.TrackChanges, title = "Invicto", isUnlocked = isInvicto) }
                        item { BadgeItem(icon = Icons.Default.Gamepad, title = "Veterano", isUnlocked = isVeterano) }
                        item { BadgeItem(icon = Icons.Default.School, title = "Mestre", isUnlocked = isMestre) }
                    }
                }

                item { Spacer(modifier = Modifier.height(32.dp)) }
            }
        }
    }
}

// 🃏 Componente para os quadradinhos de estatísticas
@Composable
fun StatBox(modifier: Modifier = Modifier, icon: ImageVector, title: String, value: String, color: Color) {
    Card(
        modifier = modifier.height(100.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f))
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
            Text(title, color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
        }
    }
}

// 📊 Componente para a barra de progresso de cada matéria
@Composable
fun SubjectProgressBar(subject: String, progress: Float, progressStr: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(subject, color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Text(progressStr, color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.height(6.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
            color = Color(0xFF3B82F6), // Azul brilhante
            trackColor = Color.White.copy(alpha = 0.2f)
        )
    }
}

// 🎖️ Componente para as medalhas de conquista
@Composable
fun BadgeItem(icon: ImageVector, title: String, isUnlocked: Boolean) {
    val backgroundColor = if (isUnlocked) Color(0xFFFFD700).copy(alpha = 0.2f) else Color.Black.copy(alpha = 0.2f)
    val iconColor = if (isUnlocked) Color(0xFFFFD700) else Color.White.copy(alpha = 0.3f)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = title, tint = iconColor, modifier = Modifier.size(36.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            color = if (isUnlocked) Color.White else Color.White.copy(alpha = 0.4f),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}