package com.github.smalljooj.sunflowerapp.data.util

import com.github.smalljooj.sunflowerapp.data.repositories.UserRepository
import com.github.smalljooj.sunflowerapp.data.types.model.Emotion
import com.github.smalljooj.sunflowerapp.data.types.model.Question
import com.github.smalljooj.sunflowerapp.data.types.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update


suspend fun answerQuestion(
    answer: Boolean,
    question: Question,
    offlineUserRepository: UserRepository,
    user: MutableStateFlow<User>
) {
    if (answer) {
        if (question.isAnswerYes) {
            when (question.emotion) {
                Emotion.ANGER -> {
                    user.update {
                        it.copy(
                            anger = if(user.value.anger + 1 < 5)
                                user.value.anger + 1 else 5
                        )
                    }
                    offlineUserRepository.updateUser(user.value)
                }
                Emotion.DISGUST -> {
                    user.update {
                        it.copy(
                            disgust = if(user.value.disgust + 1 < 5)
                                user.value.disgust + 1 else 5
                        )
                    }
                    offlineUserRepository.updateUser(user.value)
                }
                Emotion.ANXIETY -> {
                    user.update {
                        it.copy(
                            disgust = if(user.value.anxiety + 1 < 5)
                                user.value.anxiety + 1 else 5
                        )
                    }
                    offlineUserRepository.updateUser(user.value)
                }
                Emotion.JEALOUSY -> {
                    user.update {
                        it.copy(
                            jealousy = if(user.value.jealousy + 1 < 5)
                                user.value.jealousy + 1 else 5
                        )
                    }
                    offlineUserRepository.updateUser(user.value)
                }
                Emotion.HAPPINESS -> {
                    user.update {
                        it.copy(
                            happiness = if(user.value.happiness + 1 < 5)
                                user.value.happiness + 1 else 5
                        )
                    }
                    offlineUserRepository.updateUser(user.value)
                }
                Emotion.SADNESS -> {
                    user.update {
                        it.copy(
                            sadness = if(user.value.sadness + 1 < 5)
                                user.value.sadness + 1 else 5
                        )
                    }
                    offlineUserRepository.updateUser(user.value)
                }
            }
        }
    } else {
        if (!question.isAnswerYes) {
            when (question.emotion) {
                Emotion.ANGER -> {
                    user.update {
                        it.copy(
                            anger = if(user.value.anger + 1 < 5)
                                user.value.anger + 1 else 5
                        )
                    }
                    offlineUserRepository.updateUser(user.value)
                }
                Emotion.DISGUST -> {
                    user.update {
                        it.copy(
                            disgust = if(user.value.disgust + 1 < 5)
                                user.value.disgust + 1 else 5
                        )
                    }
                    offlineUserRepository.updateUser(user.value)
                }
                Emotion.ANXIETY -> {
                    user.update {
                        it.copy(
                            disgust = if(user.value.anxiety + 1 < 5)
                                user.value.anxiety + 1 else 5
                        )
                    }
                    offlineUserRepository.updateUser(user.value)
                }
                Emotion.JEALOUSY -> {
                    user.update {
                        it.copy(
                            jealousy = if(user.value.jealousy + 1 < 5)
                                user.value.jealousy + 1 else 5
                        )
                    }
                    offlineUserRepository.updateUser(user.value)
                }
                Emotion.HAPPINESS -> {
                    user.update {
                        it.copy(
                            happiness = if(user.value.happiness + 1 < 5)
                                user.value.happiness + 1 else 5
                        )
                    }
                    offlineUserRepository.updateUser(user.value)
                }
                Emotion.SADNESS -> {
                    user.update {
                        it.copy(
                            sadness = if(user.value.sadness + 1 < 5)
                                user.value.sadness + 1 else 5
                        )
                    }
                    offlineUserRepository.updateUser(user.value)
                }
            }
        } else {
            when (question.emotion) {
                Emotion.ANGER -> {
                    user.update {
                        it.copy(
                            anger = if (user.value.anger - 1 >= 0)
                                user.value.anger - 1 else 0
                        )
                    }
                    offlineUserRepository.updateUser(user.value)
                }
                Emotion.DISGUST -> {
                    user.update {
                        it.copy(
                            disgust = if (user.value.disgust - 1 >= 0)
                                user.value.disgust - 1 else 0
                        )
                    }
                    offlineUserRepository.updateUser(user.value)
                }
                Emotion.ANXIETY -> {
                    user.update {
                        it.copy(
                            disgust = if (user.value.anxiety - 1 >= 0)
                                user.value.anxiety - 1 else 0
                        )
                    }
                    offlineUserRepository.updateUser(user.value)
                }
                Emotion.JEALOUSY -> {
                    user.update {
                        it.copy(
                            jealousy = if (user.value.jealousy - 1 >= 0)
                                user.value.jealousy - 1 else 0
                        )
                    }
                    offlineUserRepository.updateUser(user.value)
                }
                Emotion.HAPPINESS -> {
                    user.update {
                        it.copy(
                            happiness = if (user.value.happiness - 1 >= 0)
                                user.value.happiness - 1 else 0
                        )
                    }
                    offlineUserRepository.updateUser(user.value)
                }
                Emotion.SADNESS -> {
                    user.update {
                        it.copy(
                            sadness = if (user.value.sadness - 1 >= 0)
                                user.value.sadness - 1 else 0
                        )
                    }
                    offlineUserRepository.updateUser(user.value)
                }
            }
        }
    }
}
