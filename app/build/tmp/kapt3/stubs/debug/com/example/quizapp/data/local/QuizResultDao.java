package com.example.quizapp.data.local;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u001c\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\'J\u001c\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u00040\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\'J\u0016\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\r\u00a8\u0006\u000e"}, d2 = {"Lcom/example/quizapp/data/local/QuizResultDao;", "", "getAverageScoreBySubject", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/example/quizapp/data/local/SubjectStat;", "email", "", "getResultsByUser", "Lcom/example/quizapp/data/model/QuizResult;", "insertResult", "", "result", "(Lcom/example/quizapp/data/model/QuizResult;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface QuizResultDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertResult(@org.jetbrains.annotations.NotNull()
    com.example.quizapp.data.model.QuizResult result, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM quiz_results WHERE userEmail = :email ORDER BY dateTimestamp DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.quizapp.data.model.QuizResult>> getResultsByUser(@org.jetbrains.annotations.NotNull()
    java.lang.String email);
    
    @androidx.room.Query(value = "SELECT subject, AVG(score) as averageScore FROM quiz_results WHERE userEmail = :email GROUP BY subject")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.quizapp.data.local.SubjectStat>> getAverageScoreBySubject(@org.jetbrains.annotations.NotNull()
    java.lang.String email);
}