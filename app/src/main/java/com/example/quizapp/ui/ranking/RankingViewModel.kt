package com.example.quizapp.ui.ranking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.model.QuizResult
import com.example.quizapp.data.repository.QuizRepository // <-- Import do seu repositório
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val repository: QuizRepository // <-- Agora ele fala com a nuvem!
) : ViewModel() {

    private var allResults: List<QuizResult> = emptyList()

    private val _topScores = MutableStateFlow<List<QuizResult>>(emptyList())
    val topScores: StateFlow<List<QuizResult>> = _topScores.asStateFlow()

    private val _selectedSubject = MutableStateFlow("Matemática")
    val selectedSubject: StateFlow<String> = _selectedSubject.asStateFlow()

    private val _availableSubjects = MutableStateFlow<List<String>>(emptyList())
    val availableSubjects: StateFlow<List<String>> = _availableSubjects.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Busca TUDO do Firebase (Ranking Global)
    fun loadGlobalRanking() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Puxa as partidas de todos os usuários do mundo
                allResults = repository.getAllGlobalResults()

                // Configura as matérias disponíveis
                val subjects = allResults.map { it.subject }.distinct()
                if (subjects.isNotEmpty()) {
                    _availableSubjects.value = subjects
                    if (_selectedSubject.value !in subjects) {
                        _selectedSubject.value = subjects.first()
                    }
                }

                // Filtra e agrupa as notas
                filterBySubject(_selectedSubject.value)
            } catch (e: Exception) {
                println("Erro ao buscar ranking global: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun filterBySubject(subject: String) {
        _selectedSubject.value = subject

        val filteredList = allResults
            .filter { it.subject == subject }
            .groupBy { it.userEmail }         // Agrupa pelo e-mail
            .map { (_, userResults) ->
                userResults.maxByOrNull { it.score }!! // Pega a maior nota
            }
            .sortedByDescending { it.score }  // Ordena do maior pro menor
            .take(10)                         // Pega o Top 10

        _topScores.value = filteredList
    }
}