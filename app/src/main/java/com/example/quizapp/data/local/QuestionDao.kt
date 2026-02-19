package com.example.quizapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.quizapp.data.model.Question

@Dao // <-- O Room precisa muito disso
interface QuestionDao { // <-- Tem que ser interface, não class!

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<Question>)

    @Query("SELECT * FROM questions WHERE subject = :subject")
    suspend fun getQuestionsBySubject(subject: String): List<Question>

    @Query("SELECT * FROM questions")
    suspend fun getAllQuestions(): List<Question>
}