package com.example.quizapp.ui.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.model.Question
import com.example.quizapp.data.model.QuizResult
import com.example.quizapp.data.repository.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Estado que representa a tela de Quiz.
 * Essencial para o Requisito 3 e 5 do PDF (Execução e Interface). [cite: 26, 32]
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
     * Implementa a lógica de sincronização offline/online (Requisito 2). [cite: 22, 24]
     */
    fun loadQuestions(subject: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val result = repository.getQuestionsBySubject(subject)
                _uiState.update { it.copy(questions = result, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = "Erro ao carregar questões.") }
            }
        }
    }

    /**
     * Processa a resposta do usuário e avança para a próxima pergunta.
     * Controla o desempenho em tempo real (Requisito 3). [cite: 26, 28]
     */
    fun submitAnswer(selectedOptionIndex: Int) {
        val currentState = _uiState.value
        val currentQuestion = currentState.questions.getOrNull(currentState.currentQuestionIndex) ?: return

        // Verifica se a resposta está correta
        val isCorrect = selectedOptionIndex == currentQuestion.correctOptionIndex
        val newScore = if (isCorrect) currentState.score + 1 else currentState.score

        // Verifica se há mais perguntas (Limite de 10 conforme o repositório)
        if (currentState.currentQuestionIndex + 1 < currentState.questions.size) {
            _uiState.update {
                it.copy(
                    currentQuestionIndex = it.currentQuestionIndex + 1,
                    score = newScore
                )
            }
        } else {
            // Quiz finalizado (Requisito 3.2) [cite: 28]
            _uiState.update { it.copy(score = newScore, isFinished = true) }
        }
    }

    /**
     * Salva o resultado final localmente e na nuvem.
     * Atende ao Requisito 3.3 do PDF.
     */
    fun saveFinalResult(userEmail: String, subject: String) {
        val state = _uiState.value
        val result = QuizResult(
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
                // Em caso de erro na nuvem, o Repository já tratou o salvamento local
                _uiState.update { it.copy(errorMessage = "Erro ao sincronizar com a nuvem.") }
            }
        }
    }

    /**
     * Popula o banco de dados (Requisito para testes iniciais).
     * Delegado ao Repository para manter o MVVM Puro.
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
                // Adicionar as demais questões aqui...
            )
            repository.seedDatabase(schoolQuestions)
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}