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
            quizResultDao.insertResult(result) // Local
            // ALINHAMOS O NOME DA PASTA AQUI EMBAIXO! 👇
            firestore.collection("quiz_results").add(result).await() // Nuvem
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
    // Adicione esta função dentro do seu QuizRepository
    suspend fun getAllGlobalResults(): List<QuizResult> {
        return try {
            val snapshot = firestore.collection("quiz_results").get().await()

            // Mapeamento manual para evitar o erro de construtor do Firebase
            snapshot.documents.mapNotNull { doc ->
                try {
                    QuizResult(
                        // Lemos cada campo diretamente do documento da nuvem
                        id = doc.getLong("id")?.toInt() ?: 0,
                        userEmail = doc.getString("userEmail") ?: "Desconhecido",
                        subject = doc.getString("subject") ?: "",
                        score = doc.getLong("score")?.toInt() ?: 0,
                        totalQuestions = doc.getLong("totalQuestions")?.toInt() ?: 0,
                        dateTimestamp = doc.getLong("dateTimestamp") ?: 0L
                    )
                } catch (e: Exception) {
                    null // Se algum documento estiver mal formatado, ignora-o
                }
            }
        } catch (e: Exception) {
            e.printStackTrace() // Vai imprimir o erro no Logcat caso a internet falhe
            emptyList()
        }
    }
    // Adicione esta função no final do seu QuizRepository.kt
    suspend fun getUserHistoryFromFirebase(email: String): List<QuizResult> {
        return try {
            val snapshot = firestore.collection("quiz_results")
                .whereEqualTo("userEmail", email) // Filtra apenas o e-mail do usuário logado!
                .get()
                .await()

            snapshot.documents.mapNotNull { doc ->
                try {
                    QuizResult(
                        id = doc.getLong("id")?.toInt() ?: 0,
                        userEmail = doc.getString("userEmail") ?: "",
                        subject = doc.getString("subject") ?: "",
                        score = doc.getLong("score")?.toInt() ?: 0,
                        totalQuestions = doc.getLong("totalQuestions")?.toInt() ?: 0,
                        dateTimestamp = doc.getLong("dateTimestamp") ?: 0L
                    )
                } catch (e: Exception) { null }
            }.sortedByDescending { it.dateTimestamp } // Ordena do mais recente para o mais antigo
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}