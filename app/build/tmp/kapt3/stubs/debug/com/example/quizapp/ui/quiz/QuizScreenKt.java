package com.example.quizapp.ui.quiz;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0016\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001a$\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00062\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00010\bH\u0007\u001a&\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\t2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001a>\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\b\b\u0002\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u00a8\u0006\u0016"}, d2 = {"EmptyState", "", "onPopulate", "Lkotlin/Function0;", "QuizContent", "state", "Lcom/example/quizapp/ui/quiz/QuizUiState;", "onAnswerSelected", "Lkotlin/Function1;", "", "QuizResultScreen", "score", "total", "onFinish", "QuizScreen", "subject", "", "userEmail", "viewModel", "Lcom/example/quizapp/ui/quiz/QuizViewModel;", "onNavigateToProfile", "onNavigateBack", "app_debug"})
public final class QuizScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void QuizScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String subject, @org.jetbrains.annotations.NotNull()
    java.lang.String userEmail, @org.jetbrains.annotations.NotNull()
    com.example.quizapp.ui.quiz.QuizViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToProfile, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void QuizContent(@org.jetbrains.annotations.NotNull()
    com.example.quizapp.ui.quiz.QuizUiState state, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onAnswerSelected) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void QuizResultScreen(int score, int total, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onFinish) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void EmptyState(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onPopulate) {
    }
}