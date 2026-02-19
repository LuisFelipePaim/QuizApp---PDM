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
class SignUpViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    // Única fonte de verdade para o estado da tela
    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState = _uiState.asStateFlow()

    // Agora recebemos os dados diretamente da View (Tela)
    fun onSignUpClick(email: String, password: String, name: String) {
        if (password.length < 6) {
            _uiState.value = AuthUiState.Error("A senha deve ter pelo menos 6 caracteres")
            return
        }

        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading

            // Chama o repositório passando as variáveis corretas
            val result = repository.signUp(email = email, pass = password, name = name)

            if (result.isSuccess) {
                _uiState.value = AuthUiState.Success
            } else {
                _uiState.value = AuthUiState.Error(result.exceptionOrNull()?.message ?: "Erro ao cadastrar")
            }
        }
    }
}