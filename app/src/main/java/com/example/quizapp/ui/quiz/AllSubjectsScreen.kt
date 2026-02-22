package com.example.quizapp.ui.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllSubjectsScreen(
    onNavigateBack: () -> Unit,
    onNavigateToQuiz: (String) -> Unit
) {
    val allSubjects = listOf(
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Todas as Matérias", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.Transparent
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundGradient)
                .padding(padding)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(allSubjects) { subject ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .clickable { onNavigateToQuiz(subject.name) },
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f))
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(subject.icon, contentDescription = null, tint = subject.color, modifier = Modifier.size(40.dp))
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(subject.name, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                    }
                }
            }
        }
    }
}