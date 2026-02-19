package com.example.quizapp.data.model

import java.util.UUID

object QuizDataSeeder {

    // Função utilitária para gerar IDs únicos para o Firebase
    private fun generateId() = UUID.randomUUID().toString()
    val matematicaQuestions = listOf(
        Question(id = UUID.randomUUID().toString(), text = "Quanto é 7 x 8?", options = listOf("54", "56", "62", "64"), correctOptionIndex = 1, difficulty = "Fácil", subject = "Matemática"),
        Question(id = UUID.randomUUID().toString(), text = "Qual o valor de Pi (aproximado)?", options = listOf("3.14", "2.14", "3.16", "4.13"), correctOptionIndex = 0, difficulty = "Fácil", subject = "Matemática"),
        Question(id = UUID.randomUUID().toString(), text = "Qual a raiz quadrada de 144?", options = listOf("10", "12", "14", "16"), correctOptionIndex = 1, difficulty = "Fácil", subject = "Matemática"),
        Question(id = UUID.randomUUID().toString(), text = "Quanto é 15% de 200?", options = listOf("15", "20", "30", "45"), correctOptionIndex = 2, difficulty = "Médio", subject = "Matemática"),
        Question(id = UUID.randomUUID().toString(), text = "Qual é o resultado de 5 + 3 x 2?", options = listOf("16", "11", "10", "15"), correctOptionIndex = 1, difficulty = "Médio", subject = "Matemática"), // Pegadinha clássica de ordem de precedência
        Question(id = UUID.randomUUID().toString(), text = "Se x + 5 = 12, qual o valor de x?", options = listOf("5", "6", "7", "8"), correctOptionIndex = 2, difficulty = "Fácil", subject = "Matemática"),
        Question(id = UUID.randomUUID().toString(), text = "Quantos graus tem um círculo completo?", options = listOf("90", "180", "270", "360"), correctOptionIndex = 3, difficulty = "Fácil", subject = "Matemática"),
        Question(id = UUID.randomUUID().toString(), text = "Qual é a fórmula da área do triângulo?", options = listOf("b x h", "(b x h) / 2", "b + h", "l x l"), correctOptionIndex = 1, difficulty = "Médio", subject = "Matemática"),
        Question(id = UUID.randomUUID().toString(), text = "O que é um triângulo isósceles?", options = listOf("3 lados iguais", "Nenhum lado igual", "2 lados iguais", "Possui um ângulo reto"), correctOptionIndex = 2, difficulty = "Médio", subject = "Matemática"),
        Question(id = UUID.randomUUID().toString(), text = "Qual é o próximo número da sequência: 2, 4, 8, 16...?", options = listOf("20", "24", "32", "64"), correctOptionIndex = 2, difficulty = "Fácil", subject = "Matemática")
    )

    val cienciasQuestions = listOf(
        Question(id = UUID.randomUUID().toString(), text = "Qual o planeta mais próximo do Sol?", options = listOf("Vênus", "Marte", "Mercúrio", "Terra"), correctOptionIndex = 2, difficulty = "Fácil", subject = "Ciências"),
        Question(id = UUID.randomUUID().toString(), text = "Qual gás as plantas absorvem na fotossíntese?", options = listOf("Oxigênio", "Gás Carbônico", "Nitrogênio", "Hidrogênio"), correctOptionIndex = 1, difficulty = "Fácil", subject = "Ciências"),
        // Adicione aqui as outras 8 de ciências...
    )

    // Você pode criar listas para "História", "Português", "Geografia", etc.

    // Lista completa com todas as matérias juntas para enviar ao banco
    fun getAllQuestions(): List<Question> {
        return matematicaQuestions + cienciasQuestions
    }
}