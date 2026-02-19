package com.example.quizapp.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    // Retorna o e-mail do usuário logado para as queries do Room
    fun getCurrentUserEmail(): String? = auth.currentUser?.email

    suspend fun login(email: String, pass: String): Result<Boolean> {
        return try {
            auth.signInWithEmailAndPassword(email, pass).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Versão consolidada: Cria conta e salva perfil no Firestore
    suspend fun signUp(email: String, pass: String, name: String): Result<Boolean> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, pass).await()
            val userId = authResult.user?.uid ?: throw Exception("Erro ao obter UID")

            val userMap = hashMapOf(
                "uid" to userId,
                "email" to email,
                "name" to name,
                "score" to 0 // Pontuação inicial para o ranking
            )

            // Salva na nuvem
            firestore.collection("users").document(userId).set(userMap).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun isUserLoggedIn(): Boolean = auth.currentUser != null
    fun logout() = auth.signOut()
}