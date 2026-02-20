package com.example.quizapp.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.model.QuizResult
import com.example.quizapp.data.local.SubjectStat
import com.example.quizapp.data.repository.QuizRepository
import com.example.quizapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: QuizRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _historyState = MutableStateFlow<List<QuizResult>>(emptyList())
    val historyState: StateFlow<List<QuizResult>> = _historyState.asStateFlow()

    private val _statsState = MutableStateFlow<List<SubjectStat>>(emptyList())
    val statsState: StateFlow<List<SubjectStat>> = _statsState.asStateFlow()

    // O controle da rodinha de carregamento!
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun loadHistory() {
        // Pega o e-mail do usuário que está logado no momento
        val email = authRepository.getCurrentUserEmail()

        // FOFOCA NO LOGCAT: Mostra qual e-mail o app achou (ou se deu null)
        println("🔥 [DEBUG] Email logado no Histórico: $email")

        if (email == null) {
            _isLoading.value = false // Se não achar o e-mail, para a rodinha
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Vai na nuvem buscar as partidas desse e-mail
                val results = repository.getUserHistoryFromFirebase(email)

                // FOFOCA NO LOGCAT: Quantas partidas achou?
                println("🔥 [DEBUG] Partidas encontradas na nuvem: ${results.size}")

                _historyState.value = results

                val calculatedStats = results.groupBy { it.subject }
                    .map { (subject, quizzes) ->
                        SubjectStat(
                            subject = subject,
                            averageScore = quizzes.map { it.score }.average()
                        )
                    }
                _statsState.value = calculatedStats
            } catch (e: Exception) {
                println("🔥 [DEBUG] Erro ao carregar histórico: ${e.message}")
            } finally {
                _isLoading.value = false // Para a rodinha de girar, dando certo ou errado
            }
        }
    }
}