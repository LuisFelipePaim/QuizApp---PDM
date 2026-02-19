package com.example.quizapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.quizapp.data.model.QuizResult
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizResultDao {

    // O erro acontecia aqui por falta de reconhecimento do OnConflictStrategy
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(result: QuizResult)

    @Query("SELECT * FROM quiz_results WHERE userEmail = :email ORDER BY dateTimestamp DESC")
    fun getResultsByUser(email: String): Flow<List<QuizResult>>

    // Essa query alimenta o Dashboard
    @Query("SELECT subject, AVG(score) as averageScore FROM quiz_results WHERE userEmail = :email GROUP BY subject")
    fun getAverageScoreBySubject(email: String): Flow<List<SubjectStat>>
}