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
import com.example.quizapp.ui.quiz.QuizScreen
import dagger.hilt.android.AndroidEntryPoint

// Essa anotação é OBRIGATÓRIA para o Hilt (Injeção de dependência) funcionar na Activity!
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
                    // AQUI ESTÁ A MÁGICA! O "GPS" do nosso aplicativo sendo ligado:
                    val navController = rememberNavController()

                    // O NavHost é o mapa que diz quais rotas (telas) existem
                    NavHost(navController = navController, startDestination = "login") {

                        // 1. Rota de Login
                        composable("login") {
                            LoginScreen(
                                onNavigateToHome = {
                                    // Vai para a tela principal e não deixa o usuário voltar pro login ao apertar o botão "Voltar"
                                    navController.navigate("history") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                }
                                // Se a sua LoginScreen tiver um botão de "Criar Conta", descomente a linha abaixo:
                                // , onNavigateToSignUp = { navController.navigate("signup") }
                            )
                        }

                        // 2. Rota de Cadastro (SignUp)
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

                        // 3. Rota de Histórico / Dashboard
                        composable("history") {
                            HistoryScreen()
                            // No futuro, podemos colocar um botão aqui para iniciar o Quiz:
                            // onStartQuiz = { navController.navigate("quiz") }
                        }

                        // 4. Rota do Quiz
                        composable("quiz") {
                            QuizScreen(
                                subject = "Matemática", // Fixo por enquanto, o Estudante C pode deixar dinâmico depois
                                userEmail = "teste@teste.com",
                                onNavigateToProfile = { navController.navigate("history") },
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }

                        // NOTA: Se você tiver a ProfileScreen, pode adicionar um composable("profile") { ... } aqui!
                    }
                }
            }
        }
    }
}