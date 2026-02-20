package com.example.quizapp.data.repository

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    @ApplicationContext private val context: Context // <-- Injetamos o Contexto do Android aqui!
) {
    // Cria ou acede ao ficheiro local (SharedPreferences) do nosso app
    private val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    // Retorna o e-mail do utilizador logado para as queries do Room
    fun getCurrentUserEmail(): String? = auth.currentUser?.email

    suspend fun login(email: String, pass: String): Result<Boolean> {
        return try {
            auth.signInWithEmailAndPassword(email, pass).await()

            // ✨ REQUISITO 1: SALVANDO O PERFIL LOCALMENTE NO LOGIN
            sharedPreferences.edit()
                .putString("user_email", email)
                .apply()

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

            // Salva na nuvem (Firebase)
            firestore.collection("users").document(userId).set(userMap).await()

            // ✨ REQUISITO 1: SALVANDO O PERFIL LOCALMENTE NO CADASTRO
            sharedPreferences.edit()
                .putString("user_name", name)
                .putString("user_email", email)
                .apply()

            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun isUserLoggedIn(): Boolean = auth.currentUser != null

    fun logout() {
        auth.signOut()

        // ✨ Limpa os dados locais ao sair da conta!
        sharedPreferences.edit().clear().apply()
    }
}