package com.example.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quizapp.ui.auth.LoginScreen
import com.example.quizapp.ui.auth.SignUpScreen
import com.example.quizapp.ui.history.HistoryScreen
import com.example.quizapp.ui.profile.ProfileScreen
import com.example.quizapp.ui.quiz.QuizScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "login") {

                        composable("login") {
                            LoginScreen(
                                onNavigateToHome = {
                                    navController.navigate("history") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                },
                                // ADICIONE ESTA LINHA ABAIXO:
                                onNavigateToSignUp = { navController.navigate("signup") }
                            )
                        }

                        composable("signup") {
                            SignUpScreen(
                                onNavigateToLogin = { navController.popBackStack() },
                                onSignUpSuccess = {
                                    navController.navigate("login") {
                                        popUpTo("signup") { inclusive = true }
                                    }
                                }
                            )
                        }

                        // Atualize a rota do history para receber a nova navegação
                        composable("history") {
                            HistoryScreen(
                                onNavigateToProfile = { navController.navigate("profile") },
                                onStartQuiz = { navController.navigate("quiz") },
                                onNavigateToRanking = { navController.navigate("ranking") } // <--- ADICIONE ISSO
                            )
                        }

// ADICIONE A TELA DE RANKING AQUI
                        composable("ranking") {
                            com.example.quizapp.ui.ranking.RankingScreen(
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }

                        composable("profile") {
                            ProfileScreen(
                                onBack = { navController.popBackStack() },
                                onLogout = {
                                    navController.navigate("login") {
                                        popUpTo(0) // Limpa toda a pilha de telas ao deslogar
                                    }
                                }
                            )
                        }

                        composable("quiz") {
                            QuizScreen(
                                subject = "Matemática", // Por enquanto, o quiz será de Matemática
                                userEmail = "teste@teste.com",
                                onNavigateToProfile = { navController.navigate("history") },
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}