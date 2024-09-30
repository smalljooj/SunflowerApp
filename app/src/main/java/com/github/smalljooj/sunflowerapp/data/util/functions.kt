package com.github.smalljooj.sunflowerapp.data.util

import android.util.Log
import com.github.smalljooj.sunflowerapp.R
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
                }
                Emotion.DISGUST -> {
                    user.update {
                        it.copy(
                            disgust = if(user.value.disgust + 1 < 5)
                                user.value.disgust + 1 else 5
                        )
                    }
                }
                Emotion.ANXIETY -> {
                    user.update {
                        it.copy(
                            anxiety = if(user.value.anxiety + 1 < 5)
                                user.value.anxiety + 1 else 5
                        )
                    }
                }
                Emotion.JEALOUSY -> {
                    user.update {
                        it.copy(
                            jealousy = if(user.value.jealousy + 1 < 5)
                                user.value.jealousy + 1 else 5
                        )
                    }
                }
                Emotion.HAPPINESS -> {
                    user.update {
                        it.copy(
                            happiness = if(user.value.happiness + 1 < 5)
                                user.value.happiness + 1 else 5
                        )
                    }
                }
                Emotion.SADNESS -> {
                    user.update {
                        it.copy(
                            sadness = if(user.value.sadness + 1 < 5)
                                user.value.sadness + 1 else 5
                        )
                    }
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
                }
                Emotion.DISGUST -> {
                    user.update {
                        it.copy(
                            disgust = if(user.value.disgust + 1 < 5)
                                user.value.disgust + 1 else 5
                        )
                    }
                }
                Emotion.ANXIETY -> {
                    user.update {
                        it.copy(
                            anxiety = if(user.value.anxiety + 1 < 5)
                                user.value.anxiety + 1 else 5
                        )
                    }
                }
                Emotion.JEALOUSY -> {
                    user.update {
                        it.copy(
                            jealousy = if(user.value.jealousy + 1 < 5)
                                user.value.jealousy + 1 else 5
                        )
                    }
                }
                Emotion.HAPPINESS -> {
                    user.update {
                        it.copy(
                            happiness = if(user.value.happiness + 1 < 5)
                                user.value.happiness + 1 else 5
                        )
                    }
                }
                Emotion.SADNESS -> {
                    user.update {
                        it.copy(
                            sadness = if(user.value.sadness + 1 < 5)
                                user.value.sadness + 1 else 5
                        )
                    }
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
                }
                Emotion.DISGUST -> {
                    user.update {
                        it.copy(
                            disgust = if (user.value.disgust - 1 >= 0)
                                user.value.disgust - 1 else 0
                        )
                    }
                }
                Emotion.ANXIETY -> {
                    user.update {
                        it.copy(
                            anxiety = if (user.value.anxiety - 1 >= 0)
                                user.value.anxiety - 1 else 0
                        )
                    }
                }
                Emotion.JEALOUSY -> {
                    user.update {
                        it.copy(
                            jealousy = if (user.value.jealousy - 1 >= 0)
                                user.value.jealousy - 1 else 0
                        )
                    }
                }
                Emotion.HAPPINESS -> {
                    user.update {
                        it.copy(
                            happiness = if (user.value.happiness - 1 >= 0)
                                user.value.happiness - 1 else 0
                        )
                    }
                }
                Emotion.SADNESS -> {
                    user.update {
                        it.copy(
                            sadness = if (user.value.sadness - 1 >= 0)
                                user.value.sadness - 1 else 0
                        )
                    }
                }
            }
        }
    }
    if (user.value.anger > user.value.happiness &&
        user.value.anger > user.value.disgust &&
        user.value.anger > user.value.anxiety &&
        user.value.anger > user.value.sadness &&
        user.value.anger > user.value.jealousy
    ) {
        if (user.value.image == R.drawable.avatar_1 ||
            user.value.image == R.drawable.avatar_2 ||
            user.value.image == R.drawable.avatar_3 ||
            user.value.image == R.drawable.avatar_4 ||
            user.value.image == R.drawable.avatar_5 ||
            user.value.image == R.drawable.avatar_6 ||
            user.value.image == R.drawable.avatar_7
        ) {
            user.update {
                it.copy(
                    image = R.drawable.avatar_4
                )
            }
        }
    } else if (user.value.disgust > user.value.happiness &&
        user.value.disgust > user.value.anger &&
        user.value.disgust > user.value.anxiety &&
        user.value.disgust > user.value.sadness &&
        user.value.disgust > user.value.jealousy
    ) {
        if (user.value.image == R.drawable.avatar_1 ||
            user.value.image == R.drawable.avatar_2 ||
            user.value.image == R.drawable.avatar_3 ||
            user.value.image == R.drawable.avatar_4 ||
            user.value.image == R.drawable.avatar_5 ||
            user.value.image == R.drawable.avatar_6 ||
            user.value.image == R.drawable.avatar_7
        ) {
            user.update {
                it.copy(
                    image = R.drawable.avatar_3
                )
            }
        }
    } else if (user.value.anxiety > user.value.happiness &&
            user.value.anxiety > user.value.anger &&
            user.value.anxiety > user.value.disgust &&
            user.value.anxiety > user.value.sadness &&
            user.value.anxiety > user.value.jealousy
    ) {
        if (user.value.image == R.drawable.avatar_1 ||
            user.value.image == R.drawable.avatar_2 ||
            user.value.image == R.drawable.avatar_3 ||
            user.value.image == R.drawable.avatar_4 ||
            user.value.image == R.drawable.avatar_5 ||
            user.value.image == R.drawable.avatar_6 ||
            user.value.image == R.drawable.avatar_7
        ) {
            user.update {
                it.copy(
                    image = R.drawable.avatar_7
                )
            }
        }
    } else if (user.value.jealousy > user.value.happiness &&
        user.value.jealousy > user.value.anger &&
        user.value.jealousy > user.value.disgust &&
        user.value.jealousy > user.value.sadness &&
        user.value.jealousy > user.value.anxiety
    ) {
        if (user.value.image == R.drawable.avatar_1 ||
            user.value.image == R.drawable.avatar_2 ||
            user.value.image == R.drawable.avatar_3 ||
            user.value.image == R.drawable.avatar_4 ||
            user.value.image == R.drawable.avatar_5 ||
            user.value.image == R.drawable.avatar_6 ||
            user.value.image == R.drawable.avatar_7
        ) {
            user.update {
                it.copy(
                    image = R.drawable.avatar_6
                )
            }
        }
    } else if (user.value.happiness > user.value.jealousy &&
        user.value.happiness > user.value.anger &&
        user.value.happiness > user.value.disgust &&
        user.value.happiness > user.value.sadness &&
        user.value.happiness > user.value.anxiety
    ) {
        if (user.value.image == R.drawable.avatar_1 ||
            user.value.image == R.drawable.avatar_2 ||
            user.value.image == R.drawable.avatar_3 ||
            user.value.image == R.drawable.avatar_4 ||
            user.value.image == R.drawable.avatar_5 ||
            user.value.image == R.drawable.avatar_6 ||
            user.value.image == R.drawable.avatar_7
        ) {
            user.update {
                it.copy(
                    image = R.drawable.avatar_2
                )
            }
        }
    } else if (user.value.sadness > user.value.jealousy &&
        user.value.sadness > user.value.anger &&
        user.value.sadness > user.value.disgust &&
        user.value.sadness > user.value.happiness &&
        user.value.sadness > user.value.anxiety
    ) {
        if (user.value.image == R.drawable.avatar_1 ||
            user.value.image == R.drawable.avatar_2 ||
            user.value.image == R.drawable.avatar_3 ||
            user.value.image == R.drawable.avatar_4 ||
            user.value.image == R.drawable.avatar_5 ||
            user.value.image == R.drawable.avatar_6 ||
            user.value.image == R.drawable.avatar_7
        ) {
            user.update {
                it.copy(
                    image = R.drawable.avatar_5
                )
            }
        }
    }
    offlineUserRepository.updateUser(user.value)
}
