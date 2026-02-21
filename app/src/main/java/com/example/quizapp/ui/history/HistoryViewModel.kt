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
    // ✅ Agora as duas telas vão funcionar:
    val historyState: StateFlow<List<QuizResult>> = _historyState.asStateFlow()
    val results: StateFlow<List<QuizResult>> = _historyState.asStateFlow()

    private val _statsState = MutableStateFlow<List<SubjectStat>>(emptyList())
    val statsState: StateFlow<List<SubjectStat>> = _statsState.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Carrega os dados assim que o ViewModel é criado
    init {
        loadHistory()
    }

    fun loadHistory() {
        val email = authRepository.getCurrentUserEmail()
        if (email == null) {
            _isLoading.value = false
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val resultList = repository.getUserHistoryFromFirebase(email)
                // Ordena pelos mais recentes
                val sorted = resultList.sortedByDescending { it.dateTimestamp }
                _historyState.value = sorted

                // Cálculo das estatísticas
                val calculatedStats = sorted.groupBy { it.subject }
                    .map { (subject, quizzes) ->
                        SubjectStat(subject = subject, averageScore = quizzes.map { it.score }.average())
                    }
                _statsState.value = calculatedStats
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}