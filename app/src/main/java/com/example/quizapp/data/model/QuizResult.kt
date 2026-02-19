package com.example.quizapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_results")
data class QuizResult(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Essencial para o Room
    val userEmail: String,
    val subject: String,
    val score: Int,
    val totalQuestions: Int,
    val dateTimestamp: Long
)