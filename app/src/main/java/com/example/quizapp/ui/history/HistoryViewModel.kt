package com.example.quizapp.ui.history
import com.example.quizapp.data.model.QuizResult // Tem que ser .model
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.local.SubjectStat
import com.example.quizapp.data.repository.AuthRepository
import com.example.quizapp.data.repository.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val quizRepository: QuizRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    // Recupera o email do usuário logado para filtrar os dados [cite: 21]
    private val userEmail = authRepository.getCurrentUserEmail() ?: ""

    // Estado da lista de resultados (Histórico Pessoal) [cite: 30, 31]
    val historyState: StateFlow<List<QuizResult>> = quizRepository.getUserHistory(userEmail)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Estado das estatísticas (Dashboard)
    val statsState: StateFlow<List<SubjectStat>> = quizRepository.getAverageStats(userEmail)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}