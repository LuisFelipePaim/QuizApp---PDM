package com.example.quizapp.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun onLoginClick(email: String, pass: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading // Liga a rodinha

            try {
                // Tenta fazer login, MAS com um limite máximo de 5 segundos (5000 milissegundos)
                val resultado = withTimeoutOrNull(5000L) {
                    repository.login(email, pass)
                }

                // Analisa o que aconteceu
                if (resultado == null) {
                    // Demorou mais de 5 segundos (provavelmente sem internet)
                    _uiState.value = AuthUiState.Error("Sem conexão com a internet. Tente novamente.")
                } else if (resultado.isSuccess) {
                    // Deu tudo certo!
                    _uiState.value = AuthUiState.Success
                } else {
                    // Firebase devolveu um erro rápido (ex: senha incorreta)
                    val erro = resultado.exceptionOrNull()
                    _uiState.value = AuthUiState.Error(erro?.localizedMessage ?: "Erro ao fazer login.")
                }
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error("Erro inesperado. Verifique sua conexão.")
            }
        }
    }
    // Adicione isto dentro do AuthViewModel:

    // Dentro da classe AuthViewModel
    fun getCurrentUserEmail(): String? {
        return repository.getCurrentUserEmail()
    }

    fun signOut() {
        repository.logout()
    }
}

// ✨ A RECEITA DO ESTADO ADICIONADA AQUI NO FINAL! ✨
// Isso resolve o texto vermelho na sua LoginScreen.
sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    object Success : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}