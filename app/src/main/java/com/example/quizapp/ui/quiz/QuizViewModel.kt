package com.example.quizapp.ui.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.model.Question
import com.example.quizapp.data.model.QuizDataSeeder
import com.example.quizapp.data.model.QuizResult
import com.example.quizapp.data.repository.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject

/**
 * Estado que representa a tela de Quiz.
 */
data class QuizUiState(
    val questions: List<Question> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val score: Int = 0,
    val isLoading: Boolean = false,
    val isFinished: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val repository: QuizRepository
) : ViewModel() {

    // Única fonte de verdade para a View
    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    /**
     * Carrega as questões filtradas por matéria.
     * VERSÃO BLINDADA: Com timeout e finally para não travar offline.
     */
    fun loadQuestions(subject: String) {
        viewModelScope.launch {
            // 1. Liga a rodinha
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                // 2. Tenta buscar da nuvem/local com limite de 3 segundos
                val result = withTimeoutOrNull(3000L) {
                    repository.getQuestionsBySubject(subject)
                }

                // Se o resultado for nulo (timeout), usa lista vazia para não quebrar
                val finalQuestions = result ?: emptyList()

                _uiState.update { it.copy(questions = finalQuestions) }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = "Erro ao carregar questões.") }
            } finally {
                // ✨ A TRAVA DE SEGURANÇA ✨
                // Aconteça o que acontecer, desliga a rodinha!
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    /**
     * Processa a resposta do usuário e avança para a próxima pergunta.
     */
    fun submitAnswer(selectedOptionIndex: Int) {
        val currentState = _uiState.value
        val currentQuestion = currentState.questions.getOrNull(currentState.currentQuestionIndex) ?: return

        // Verifica se a resposta está correta
        val isCorrect = selectedOptionIndex == currentQuestion.correctOptionIndex
        val newScore = if (isCorrect) currentState.score + 1 else currentState.score

        // Verifica se há mais perguntas
        if (currentState.currentQuestionIndex + 1 < currentState.questions.size) {
            _uiState.update {
                it.copy(
                    currentQuestionIndex = it.currentQuestionIndex + 1,
                    score = newScore
                )
            }
        } else {
            // Quiz finalizado
            _uiState.update { it.copy(score = newScore, isFinished = true) }
        }
    }

    /**
     * Salva o resultado final localmente e na nuvem.
     */
    fun saveFinalResult(userEmail: String, subject: String) {
        val state = _uiState.value
        val result = QuizResult(
            id = 0,
            userEmail = userEmail,
            subject = subject,
            score = state.score,
            totalQuestions = state.questions.size,
            dateTimestamp = System.currentTimeMillis()
        )

        viewModelScope.launch {
            try {
                repository.saveQuizResult(result)
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = "Erro ao sincronizar com a nuvem.") }
            }
        }
    }

    /**
     * Popula o banco de dados (Requisito para testes iniciais).
     */
    fun addSampleQuestions() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val schoolQuestions = listOf(
                Question(
                    text = "Quanto é 7 x 8?",
                    options = listOf("54", "56", "62", "64"),
                    correctOptionIndex = 1,
                    difficulty = "Fácil",
                    subject = "Matemática"
                ),
                Question(
                    text = "Qual o planeta mais próximo do Sol?",
                    options = listOf("Vênus", "Marte", "Mercúrio", "Terra"),
                    correctOptionIndex = 2,
                    difficulty = "Médio",
                    subject = "Ciências"
                )
            )
            try {
                repository.seedDatabase(schoolQuestions)
            } catch (e: Exception) {
                println("Erro: ${e.message}")
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun populateFirebaseDatabase() {
        viewModelScope.launch {
            try {
                val allQuestions = QuizDataSeeder.getAllQuestions()
                repository.seedDatabase(allQuestions)
                println("Sucesso: Questões enviadas para o Firebase!")
            } catch (e: Exception) {
                println("Erro ao enviar questões: ${e.message}")
            }
        }
    }
}