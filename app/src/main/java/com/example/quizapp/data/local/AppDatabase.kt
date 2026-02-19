package com.example.quizapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.quizapp.data.model.Question
import com.example.quizapp.data.model.QuizResult

// Adicionei a versão (version) e exportSchema
@Database(
    entities = [Question::class, QuizResult::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
    abstract fun quizResultDao(): QuizResultDao
}