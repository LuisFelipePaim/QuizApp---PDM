package com.example.quizapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class Question(
    @PrimaryKey
    val id: String = "",
    val text: String = "",
    val options: List<String> = emptyList(),
    val correctOptionIndex: Int = 0,
    val difficulty: String = "",
    val subject: String = ""
)