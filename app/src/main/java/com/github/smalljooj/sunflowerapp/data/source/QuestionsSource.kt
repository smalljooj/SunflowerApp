package com.github.smalljooj.sunflowerapp.data.source

import com.github.smalljooj.sunflowerapp.data.types.model.Emotion
import com.github.smalljooj.sunflowerapp.data.types.model.Question

object QuestionsSource {
    val questions = listOf(
        Question(question = "Você está preocupado com alguma coisa?", emotion = Emotion.ANXIETY),
        Question(question = "Você está se sentindo sozinho hoje?", emotion = Emotion.ANGER),
        Question(question = "Algo te deixou nervoso recentemente?", emotion = Emotion.ANGER),
        Question(question = "Você está chateado com algum amigo?", emotion = Emotion.SADNESS),
        Question(question = "O dia de hoje está bonito?", emotion = Emotion.HAPPINESS),
        Question(question = "Houve algo que te deixou triste hoje?", emotion = Emotion.SADNESS),
        Question(question = "Você está com vontade de chorar hoje?", emotion = Emotion.SADNESS),
        Question(question = "Você está animado e quer fazer várias coisas?",
            emotion = Emotion.HAPPINESS),
        Question(question = "Houve algo que te fez sorrir hoje?", emotion = Emotion.HAPPINESS),
        Question(question = "Você já tentou algo novo recentemente?", isAnswerYes = false,
            emotion = Emotion.HAPPINESS),
        Question(question = "Você fez alguma coisa sem pensar?", emotion = Emotion.ANGER),
        Question(question = "Você se sente deixado de lado por um amigo?", emotion = Emotion.JEALOUSY),
        Question(question = "Existe alguma comida que você não consegue comer?",
            emotion = Emotion.DISGUST),
        Question(question = "Existe algum tipo de pessoa que você não gosta de chegar perto?" ,
            emotion = Emotion.DISGUST),
    )
}