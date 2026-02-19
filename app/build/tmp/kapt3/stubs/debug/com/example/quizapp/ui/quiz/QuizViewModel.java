package com.example.quizapp.ui.quiz;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\f\u001a\u00020\rJ\u000e\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0010J\u0006\u0010\u0011\u001a\u00020\rJ\u0016\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u0010J&\u0010\u0014\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0013\u001a\u00020\u0010J\u000e\u0010\u0018\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u0016R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u001a"}, d2 = {"Lcom/example/quizapp/ui/quiz/QuizViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/example/quizapp/data/repository/QuizRepository;", "(Lcom/example/quizapp/data/repository/QuizRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/example/quizapp/ui/quiz/QuizUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "addSampleQuestions", "", "loadQuestions", "subject", "", "populateFirebaseDatabase", "saveFinalResult", "userEmail", "saveQuizResult", "score", "", "totalQuestions", "submitAnswer", "selectedOptionIndex", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class QuizViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.quizapp.data.repository.QuizRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.quizapp.ui.quiz.QuizUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.quizapp.ui.quiz.QuizUiState> uiState = null;
    
    @javax.inject.Inject()
    public QuizViewModel(@org.jetbrains.annotations.NotNull()
    com.example.quizapp.data.repository.QuizRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.quizapp.ui.quiz.QuizUiState> getUiState() {
        return null;
    }
    
    /**
     * Carrega as questões filtradas por matéria.
     * Implementa a lógica de sincronização offline/online (Requisito 2). [cite: 22, 24]
     */
    public final void loadQuestions(@org.jetbrains.annotations.NotNull()
    java.lang.String subject) {
    }
    
    /**
     * Processa a resposta do usuário e avança para a próxima pergunta.
     * Controla o desempenho em tempo real (Requisito 3). [cite: 26, 28]
     */
    public final void submitAnswer(int selectedOptionIndex) {
    }
    
    /**
     * Salva o resultado final localmente e na nuvem.
     * Atende ao Requisito 3.3 do PDF.
     */
    public final void saveFinalResult(@org.jetbrains.annotations.NotNull()
    java.lang.String userEmail, @org.jetbrains.annotations.NotNull()
    java.lang.String subject) {
    }
    
    /**
     * Popula o banco de dados (Requisito para testes iniciais).
     * Delegado ao Repository para manter o MVVM Puro.
     */
    public final void addSampleQuestions() {
    }
    
    public final void populateFirebaseDatabase() {
    }
    
    public final void saveQuizResult(@org.jetbrains.annotations.NotNull()
    java.lang.String subject, int score, int totalQuestions, @org.jetbrains.annotations.NotNull()
    java.lang.String userEmail) {
    }
}