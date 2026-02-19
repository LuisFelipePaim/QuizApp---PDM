package com.example.quizapp.data.repository
import kotlinx.coroutines.flow.Flow
import com.example.quizapp.data.local.QuestionDao
import com.example.quizapp.data.local.QuizResultDao
import com.example.quizapp.data.model.Question
import com.example.quizapp.data.model.QuizResult
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.example.quizapp.data.local.SubjectStat

// QuizRepository.kt
class QuizRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val questionDao: QuestionDao,
    private val quizResultDao: QuizResultDao
) {
    // Requisito 2.4 e 2.5: Sincronização e Offline [cite: 24, 25]
    suspend fun getQuestionsBySubject(subject: String): List<Question> {
        return try {
            val snapshot = firestore.collection("questions")
                .whereEqualTo("subject", subject).get().await()
            val remoteQuestions = snapshot.toObjects(Question::class.java)

            if (remoteQuestions.isNotEmpty()) {
                questionDao.insertQuestions(remoteQuestions) // Atualiza banco local
            }
            // Retorna do banco local para garantir consistência
            questionDao.getQuestionsBySubject(subject).shuffled().take(10)
        } catch (e: Exception) {
            // Se falhar (offline), usa o que já está no Room
            questionDao.getQuestionsBySubject(subject).shuffled().take(10)
        }
    }

    // Requisito 31: Histórico Pessoal
    fun getUserHistory(email: String): Flow<List<QuizResult>> {
        return quizResultDao.getResultsByUser(email)
    }

    suspend fun saveQuizResult(result: QuizResult) {
        try {
            quizResultDao.insertResult(result) // Local [cite: 29]
            firestore.collection("results").add(result).await() // Nuvem [cite: 29]
        } catch (e: Exception) {
            // Em um app real, aqui agendaríamos uma tarefa com WorkManager
            // para subir o resultado assim que a internet voltasse.
        }
    }
    fun getAverageStats(email: String): Flow<List<SubjectStat>> {
        return quizResultDao.getAverageScoreBySubject(email)
    }

    suspend fun seedDatabase(questions: List<Question>) {
        val batch = firestore.batch()
        questions.forEach { q ->
            // Cria um documento novo para cada questão no Firebase
            val doc = firestore.collection("questions").document()
            batch.set(doc, q)
        }
        batch.commit().await()
    }
}