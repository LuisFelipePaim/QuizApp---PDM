package com.example.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.quizapp.ui.auth.LoginScreen
import com.example.quizapp.ui.auth.SignUpScreen
import com.example.quizapp.ui.history.HistoryScreen
import com.example.quizapp.ui.profile.ProfileScreen
import com.example.quizapp.ui.quiz.QuizScreen
import com.example.quizapp.ui.quiz.SubjectScreen
import com.example.quizapp.ui.quiz.AllSubjectsScreen
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
                                    // 1. Após o login, vai para a HOME (SubjectScreen)
                                    navController.navigate("home") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                },
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

                        // 2. NOVA TELA HOME (Sorteio de 3 matérias e Top 5 Histórico)
                        composable("home") {
                            SubjectScreen(
                                onNavigateToQuiz = { subject -> navController.navigate("quiz/$subject") },
                                onNavigateToHistory = { navController.navigate("history") },
                                onNavigateToAllSubjects = { navController.navigate("all_subjects") },
                                onNavigateToProfile = { navController.navigate("profile") },
                                onNavigateToRanking = { navController.navigate("ranking") },
                                // 🚀 ADICIONE ESTA LINHA AQUI!
                                onNavigateToDashboard = { navController.navigate("dashboard") }
                            )
                        }


                        // 3. NOVA TELA COM TODAS AS MATÉRIAS
                        composable("all_subjects") {
                            AllSubjectsScreen(
                                onNavigateBack = { navController.popBackStack() },
                                onNavigateToQuiz = { subject -> navController.navigate("quiz/$subject") }
                            )
                        }

                        composable("history") {
                            HistoryScreen(
                                onNavigateToProfile = { navController.navigate("profile") },
                                onStartQuiz = {
                                    // Volta para a home para o utilizador escolher uma matéria
                                    navController.navigate("home") {
                                        popUpTo("home") { inclusive = true }
                                    }
                                },
                                onNavigateToRanking = { navController.navigate("ranking") }
                            )
                        }

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

                        // 4. ROTA DO QUIZ ATUALIZADA (Agora recebe a matéria escolhida)
                        composable(
                            route = "quiz/{subject}",
                            arguments = listOf(navArgument("subject") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val subject = backStackEntry.arguments?.getString("subject") ?: "Matemática"

                            QuizScreen(
                                subject = subject,
                                userEmail = "", // O e-mail agora é gerido no ViewModel, não precisa passar por aqui
                                onNavigateToProfile = {
                                    // Após finalizar o quiz, vamos mostrar o histórico
                                    navController.navigate("history") {
                                        popUpTo("home")
                                    }
                                },
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }

                        composable("dashboard") {
                            com.example.quizapp.ui.profile.DashboardScreen(
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}