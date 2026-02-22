package com.example.quizapp.ui.ranking

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
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
) {
    // Mantivemos a sua lógica exata de Estados!
    val topScores by rankingViewModel.topScores.collectAsState()
    val availableSubjects by rankingViewModel.availableSubjects.collectAsState()
    val selectedSubject by rankingViewModel.selectedSubject.collectAsState()
    val isLoading by rankingViewModel.isLoading.collectAsState()

    // Dispara a busca no Firebase assim que a tela abre
    LaunchedEffect(Unit) {
        rankingViewModel.loadGlobalRanking()
    }

    // 🎨 DEGRADÊ DO FUNDO (Estilo Game)
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF3B82F6), Color(0xFF7C3AED))
    )

    Scaffold(
        containerColor = Color.Transparent, // Necessário para o degradê aparecer
        topBar = {
            TopAppBar(
                title = { Text("Ranking Global", color = Color.White, fontWeight = FontWeight.ExtraBold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundGradient)
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Ícone gigante de troféu no topo
                Icon(
                    imageVector = Icons.Filled.EmojiEvents,
                    contentDescription = "Troféu",
                    tint = Color(0xFFFFD700), // Dourado
                    modifier = Modifier
                        .size(80.dp)
                        .padding(top = 8.dp, bottom = 16.dp)
                )

                // --- BARRA DE BOTÕES DAS MATÉRIAS (Filtro Horizontal) ---
                if (availableSubjects.isNotEmpty()) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(availableSubjects) { subject ->
                            SubjectFilterChip(
                                subject = subject,
                                isSelected = subject == selectedSubject,
                                onClick = { rankingViewModel.filterBySubject(subject) }
                            )
                        }
                    }
                }

                // --- ÁREA DO PÓDIO / CARREGAMENTO ---
                if (isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color.White)
                    }
                } else if (topScores.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            "Nenhum resultado encontrado na nuvem.",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 16.sp
                        )
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        itemsIndexed(topScores) { index, result ->
                            RankingItemCard(position = index + 1, result = result)
                        }
                    }
                }
            }
        }
    }
}

// 🎨 Botãozinho estiloso para filtrar as matérias (Glassmorphism)
@Composable
fun SubjectFilterChip(subject: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) Color.White else Color.White.copy(alpha = 0.2f)
    val textColor = if (isSelected) Color(0xFF3B82F6) else Color.White

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(text = subject, color = textColor, fontWeight = FontWeight.Bold)
    }
}

// 🎨 O Card repaginado de cada jogador
@Composable
fun RankingItemCard(position: Int, result: QuizResult) {
    // Mesclamos a sua lógica de medalhas com o novo visual!
    val (medalColor, medalText) = when (position) {
        1 -> Pair(Color(0xFFFFD700), "🥇") // Ouro
        2 -> Pair(Color(0xFFC0C0C0), "🥈") // Prata
        3 -> Pair(Color(0xFFCD7F32), "🥉") // Bronze
        else -> Pair(Color.White.copy(alpha = 0.3f), "${position}º")
    }

    val isTop1 = position == 1
    val cardBackgroundColor = if (isTop1) Color.White.copy(alpha = 0.25f) else Color.White.copy(alpha = 0.15f)

    // Tratamento do nome (Esconde o "@gmail.com")
    val displayName = result.userEmail.substringBefore("@").replaceFirstChar { it.uppercase() }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cardBackgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Círculo com a Posição/Medalha
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(if (position <= 3) medalColor.copy(alpha = 0.2f) else medalColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = medalText,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = if (position <= 3) 22.sp else 16.sp,
                    color = if (position <= 3) medalColor else Color.White
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Ícone + Nome do Jogador
            Icon(Icons.Filled.Person, contentDescription = null, tint = Color.White.copy(alpha = 0.7f))
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = displayName,
                    color = Color.White,
                    fontWeight = if (isTop1) FontWeight.ExtraBold else FontWeight.SemiBold,
                    fontSize = 18.sp
                )
                Text(
                    text = result.subject,
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 12.sp
                )
            }

            // Pontuação
            Text(
                text = "${result.score} pts",
                color = if (isTop1) Color(0xFFFFD700) else Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp
            )
        }
    }
}