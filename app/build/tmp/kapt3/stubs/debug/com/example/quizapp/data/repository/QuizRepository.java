package com.example.quizapp.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0086@\u00a2\u0006\u0002\u0010\fJ\u001a\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\n0\u000e2\u0006\u0010\u0010\u001a\u00020\u0011J\u001c\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\n2\u0006\u0010\u0014\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u0010\u0015J\u001a\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\u000e2\u0006\u0010\u0010\u001a\u00020\u0011J\u001c\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\u0010\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u0010\u0015J\u0016\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u000bH\u0086@\u00a2\u0006\u0002\u0010\u001bJ\u001c\u0010\u001c\u001a\u00020\u00192\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00130\nH\u0086@\u00a2\u0006\u0002\u0010\u001eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Lcom/example/quizapp/data/repository/QuizRepository;", "", "firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "questionDao", "Lcom/example/quizapp/data/local/QuestionDao;", "quizResultDao", "Lcom/example/quizapp/data/local/QuizResultDao;", "(Lcom/google/firebase/firestore/FirebaseFirestore;Lcom/example/quizapp/data/local/QuestionDao;Lcom/example/quizapp/data/local/QuizResultDao;)V", "getAllGlobalResults", "", "Lcom/example/quizapp/data/model/QuizResult;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAverageStats", "Lkotlinx/coroutines/flow/Flow;", "Lcom/example/quizapp/data/local/SubjectStat;", "email", "", "getQuestionsBySubject", "Lcom/example/quizapp/data/model/Question;", "subject", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUserHistory", "getUserHistoryFromFirebase", "saveQuizResult", "", "result", "(Lcom/example/quizapp/data/model/QuizResult;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "seedDatabase", "questions", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
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
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getAllGlobalResults(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.quizapp.data.model.QuizResult>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getUserHistoryFromFirebase(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.quizapp.data.model.QuizResult>> $completion) {
        return null;
    }
}