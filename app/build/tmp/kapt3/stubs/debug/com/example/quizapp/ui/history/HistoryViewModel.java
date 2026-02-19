package com.example.quizapp.ui.history;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/example/quizapp/ui/history/HistoryViewModel;", "Landroidx/lifecycle/ViewModel;", "quizRepository", "Lcom/example/quizapp/data/repository/QuizRepository;", "authRepository", "Lcom/example/quizapp/data/repository/AuthRepository;", "(Lcom/example/quizapp/data/repository/QuizRepository;Lcom/example/quizapp/data/repository/AuthRepository;)V", "historyState", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/example/quizapp/data/model/QuizResult;", "getHistoryState", "()Lkotlinx/coroutines/flow/StateFlow;", "statsState", "Lcom/example/quizapp/data/local/SubjectStat;", "getStatsState", "userEmail", "", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class HistoryViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.quizapp.data.repository.QuizRepository quizRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.quizapp.data.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String userEmail = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.quizapp.data.model.QuizResult>> historyState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.quizapp.data.local.SubjectStat>> statsState = null;
    
    @javax.inject.Inject()
    public HistoryViewModel(@org.jetbrains.annotations.NotNull()
    com.example.quizapp.data.repository.QuizRepository quizRepository, @org.jetbrains.annotations.NotNull()
    com.example.quizapp.data.repository.AuthRepository authRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.quizapp.data.model.QuizResult>> getHistoryState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.quizapp.data.local.SubjectStat>> getStatsState() {
        return null;
    }
}