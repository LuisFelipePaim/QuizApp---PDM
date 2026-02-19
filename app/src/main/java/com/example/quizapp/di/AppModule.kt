package com.example.quizapp.di

import android.content.Context
import androidx.room.Room
import com.example.quizapp.data.local.AppDatabase
import com.example.quizapp.data.local.QuestionDao
import com.example.quizapp.data.local.QuizResultDao // <--- NOVO IMPORT
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "quiz_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideQuestionDao(database: AppDatabase): QuestionDao {
        return database.questionDao()
    }

    // --- FUNÇÃO ADICIONADA PARA RESOLVER O ERRO ---
    @Provides
    fun provideQuizResultDao(database: AppDatabase): QuizResultDao {
        return database.quizResultDao()
    }

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
}