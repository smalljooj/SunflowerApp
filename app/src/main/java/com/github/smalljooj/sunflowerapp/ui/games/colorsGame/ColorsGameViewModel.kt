package com.github.smalljooj.sunflowerapp.ui.games.colorsGame

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.github.smalljooj.sunflowerapp.R
import com.github.smalljooj.sunflowerapp.SunflowerApplication
import com.github.smalljooj.sunflowerapp.data.repositories.UserRepository
import com.github.smalljooj.sunflowerapp.data.source.ColorsSource
import com.github.smalljooj.sunflowerapp.data.source.QuestionsSource
import com.github.smalljooj.sunflowerapp.data.types.model.CircleColor
import com.github.smalljooj.sunflowerapp.data.types.model.User
import com.github.smalljooj.sunflowerapp.data.util.answerQuestion
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class ColorsGameViewModel(
    private val offlineUserRepository: UserRepository,
    val user: MutableStateFlow<User>
): ViewModel() {
    private val _uiState = MutableStateFlow(ColorsGameUiState())
    val uiState = _uiState.asStateFlow()
    var openQuestionDialog by mutableStateOf(false)
        private set
    var question = QuestionsSource.questions[Random.nextInt(0, 13)]
    var recomposition by mutableStateOf(true)
    var isMemorizeTime by mutableStateOf(true)
        private set
    var time by mutableIntStateOf(5)
        private set

    init {
        val randomNumbers = (0..9).shuffled().take(3)
        _uiState.update {
            it.copy(
                colorsRef = getColorsList(randomNumbers),
            )
        }
        viewModelScope.launch {
            levelInit()
        }
    }

    fun updateColor(color: Color, index: Int, context: Context) {
        MediaPlayer.create(context, R.raw.bubblesound).start()
        val newList = _uiState.value.colorsGuest.toMutableList()
        newList[index] = CircleColor(color = color, index = index)
        _uiState.update {
            it.copy(
                colorsGuest = newList
            )
        }
        verifyGame(context)
        recomposition = !recomposition
    }

    private fun verifyGame(context: Context) {
        var isFinished = true
        var isCorrect = true
        for (color in uiState.value.colorsGuest) {
            if (color.color == Color.Gray) {
                isFinished = false
                break
            }
        }
        if (isFinished) {
            for (i in (0 until uiState.value.colorsRef.size)) {
                if (uiState.value.colorsGuest[i].color !=
                    uiState.value.colorsRef[i].color) {
                    isCorrect = false
                }
            }
            if (isCorrect) {
                MediaPlayer.create(context, R.raw.winsound).start()
                nextLevel()
            } else {
                MediaPlayer.create(context, R.raw.gameover).start()
                reset()
            }
        }
    }

    private suspend fun levelInit() {
        isMemorizeTime = true
        for (i in (5 downTo 1)) {
            time = i
            delay(1000)
        }
        isMemorizeTime = false
    }

    private fun nextLevel() {
        _uiState.update {
            val size = 3 + (uiState.value.level / 3)
            val randomNumbers = (0..9).shuffled().take(size)
            it.copy(
                level = uiState.value.level + 1,
                colorsRef = getColorsList(randomNumbers),
                colorsGuest = getGrayColorsList(size).toMutableList()
            )
        }
        viewModelScope.launch {
            levelInit()
        }
    }
    private fun getGrayColorsList(size: Int): List<CircleColor> {
        val newList: MutableList<CircleColor> = mutableListOf()
        for (i in (0 until size)) {
            newList.add(CircleColor(color = Color.Gray, index = i))
        }
        return newList.toList()
    }

    private fun getColorsList(numbers: List<Int>): List<CircleColor> {
        val list: MutableList<CircleColor> = mutableListOf()
        for (element in numbers) {
            list.add(ColorsSource.colors[element])
        }
        return list.toList()
    }

    private fun reset() {
        _uiState.update {
            val randomNumbers = (0..9).shuffled().take(3)
            it.copy(
                level = 1,
                colorsRef = getColorsList(randomNumbers),
                colorsGuest = getGrayColorsList(3).toMutableList()
            )
        }
        viewModelScope.launch {
            levelInit()
        }
    }

    fun updateOpenQuestionDialog(value: Boolean) {
        openQuestionDialog = value
    }

    fun onEvent(event: ColorsGameEvent) {
        when(event) {
            ColorsGameEvent.ResetGame -> {
                reset()
                recomposition = !recomposition
            }
        }
    }

    fun answer(answer: Boolean) {
        viewModelScope.launch {
            answerQuestion(
                user = user,
                offlineUserRepository = offlineUserRepository,
                question = question,
                answer = answer
            )
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as SunflowerApplication)
                val offlineUserRepository = application.container.offlineUserRepository
                val user = application.container.user
                ColorsGameViewModel(
                    offlineUserRepository = offlineUserRepository,
                    user = user
                )
            }
        }
    }
}
