package com.example.quizapp.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun onSignUpClick(email: String, pass: String, name: String) {
        viewModelScope.launch {
            // 1. Liga a rodinha de carregamento
            _uiState.value = AuthUiState.Loading

            // 2. Chama o repositório e guarda a resposta (Result)
            val resultado = repository.signUp(email, pass, name)

            // 3. Checa se o repositório disse que foi sucesso ou falha
            if (resultado.isSuccess) {
                // Sucesso! A tela vai navegar para o Login
                _uiState.value = AuthUiState.Success
            } else {
                // Falha! Desliga a rodinha e mostra o erro exato na tela
                val erro = resultado.exceptionOrNull()
                _uiState.value = AuthUiState.Error(erro?.localizedMessage ?: "Erro ao cadastrar. Verifique os dados.")
            }
        }
    }
}