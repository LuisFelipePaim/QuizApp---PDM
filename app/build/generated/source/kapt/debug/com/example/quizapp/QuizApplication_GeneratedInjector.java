package com.example.quizapp;

import dagger.hilt.InstallIn;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.internal.GeneratedEntryPoint;

@OriginatingElement(
    topLevelClass = QuizApplication.class
)
@GeneratedEntryPoint
@InstallIn(SingletonComponent.class)
public interface QuizApplication_GeneratedInjector {
  void injectQuizApplication(QuizApplication quizApplication);
}
