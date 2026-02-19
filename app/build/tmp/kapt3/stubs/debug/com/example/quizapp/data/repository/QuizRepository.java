package com.example.quizapp.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u001a\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\n2\u0006\u0010\r\u001a\u00020\u000eJ\u001c\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u000b2\u0006\u0010\u0011\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u0012J\u001a\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u000b0\n2\u0006\u0010\r\u001a\u00020\u000eJ\u0016\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0014H\u0086@\u00a2\u0006\u0002\u0010\u0018J\u001c\u0010\u0019\u001a\u00020\u00162\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00100\u000bH\u0086@\u00a2\u0006\u0002\u0010\u001bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/example/quizapp/data/repository/QuizRepository;", "", "firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "questionDao", "Lcom/example/quizapp/data/local/QuestionDao;", "quizResultDao", "Lcom/example/quizapp/data/local/QuizResultDao;", "(Lcom/google/firebase/firestore/FirebaseFirestore;Lcom/example/quizapp/data/local/QuestionDao;Lcom/example/quizapp/data/local/QuizResultDao;)V", "getAverageStats", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/example/quizapp/data/local/SubjectStat;", "email", "", "getQuestionsBySubject", "Lcom/example/quizapp/data/model/Question;", "subject", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUserHistory", "Lcom/example/quizapp/data/model/QuizResult;", "saveQuizResult", "", "result", "(Lcom/example/quizapp/data/model/QuizResult;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "seedDatabase", "questions", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class QuizRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.firestore.FirebaseFirestore firestore = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.quizapp.data.local.QuestionDao questionDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.quizapp.data.local.QuizResultDao quizResultDao = null;
    
    @javax.inject.Inject()
    public QuizRepository(@org.jetbrains.annotations.NotNull()
    com.google.firebase.firestore.FirebaseFirestore firestore, @org.jetbrains.annotations.NotNull()
    com.example.quizapp.data.local.QuestionDao questionDao, @org.jetbrains.annotations.NotNull()
    com.example.quizapp.data.local.QuizResultDao quizResultDao) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getQuestionsBySubject(@org.jetbrains.annotations.NotNull()
    java.lang.String subject, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.quizapp.data.model.Question>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.example.quizapp.data.model.QuizResult>> getUserHistory(@org.jetbrains.annotations.NotNull()
    java.lang.String email) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object saveQuizResult(@org.jetbrains.annotations.NotNull()
    com.example.quizapp.data.model.QuizResult result, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.example.quizapp.data.local.SubjectStat>> getAverageStats(@org.jetbrains.annotations.NotNull()
    java.lang.String email) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object seedDatabase(@org.jetbrains.annotations.NotNull()
    java.util.List<com.example.quizapp.data.model.Question> questions, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}