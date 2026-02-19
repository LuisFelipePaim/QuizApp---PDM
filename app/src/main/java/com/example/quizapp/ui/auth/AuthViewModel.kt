package com.example.quizapp.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun performLogin(email: String, pass: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            val result = repository.login(email, pass)
            if (result.isSuccess) {
                _uiState.value = AuthUiState.Success
            } else {
                _uiState.value = AuthUiState.Error(result.exceptionOrNull()?.message ?: "Erro desconhecido")
            }
        }
    }

    // Retorna o e-mail para a tela de Perfil
    fun getCurrentUserEmail(): String {
        // Se for nulo, retorna uma string vazia para não quebrar o Text() do Compose
        return repository.getCurrentUserEmail() ?: "Usuário não logado"
    }

    // Faz o logout e reseta o estado da tela
    fun signOut() {
        repository.logout()
        _uiState.value = AuthUiState.Idle
    }
}

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    object Success : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}