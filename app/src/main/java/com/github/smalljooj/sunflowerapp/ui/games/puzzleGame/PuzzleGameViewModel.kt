package com.github.smalljooj.sunflowerapp.ui.games.puzzleGame

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.github.smalljooj.sunflowerapp.R
import com.github.smalljooj.sunflowerapp.SunflowerApplication
import com.github.smalljooj.sunflowerapp.data.repositories.UserRepository
import com.github.smalljooj.sunflowerapp.data.source.PuzzleImageSource
import com.github.smalljooj.sunflowerapp.data.source.QuestionsSource
import com.github.smalljooj.sunflowerapp.data.types.model.User
import com.github.smalljooj.sunflowerapp.data.util.answerQuestion
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class PuzzleGameViewModel(
    private val offlineUserRepository: UserRepository,
    val user: MutableStateFlow<User>
): ViewModel() {
    private val _uiState = MutableStateFlow(PuzzleGameUiState())
    val uiState = _uiState.asStateFlow()
    var recomposition by mutableStateOf(true)
    private var nextLevel = false
    var openQuestionDialog by mutableStateOf(false)
        private set
    var question = QuestionsSource.questions[Random.nextInt(0, 13)]

    fun updateOpenQuestionDialog(value: Boolean) {
        openQuestionDialog = value
    }

    init {
        val isGenerated = (0..2).random() == 0
        if (isGenerated) {
            updateOpenQuestionDialog(true)
            question = QuestionsSource.questions[Random.nextInt(0, 13)]
        }
    }

    fun onEvent(event: PuzzleGameEvent) {
        when(event) {
            PuzzleGameEvent.ResetGame -> {
                val isGenerated = (0..2).random() == 0
                if (isGenerated) {
                    updateOpenQuestionDialog(true)
                    question = QuestionsSource.questions[Random.nextInt(0, 13)]
                }
                _uiState.value = PuzzleGameUiState()
                recomposition = !recomposition
            }
        }
    }

    fun rotateImage(x: Int, y: Int, context: Context) {
        if (!nextLevel) {
            if (_uiState.value.orientation[x][y] < 270) {
                _uiState.value.orientation[x][y] += 90
                recomposition = !recomposition
            } else {
                _uiState.value.orientation[x][y] = 0
                recomposition = !recomposition
            }
            verifyGame(context)
        }
    }
    private fun verifyGame(context: Context) {
        nextLevel = true
        val winSound = MediaPlayer.create(context, R.raw.winsound)
        _uiState.value.orientation.forEach { it ->
           it.forEach{
               if (it != 0) {
                   nextLevel = false
               }
           }
       }
        if (nextLevel) {
            winSound.start()
            viewModelScope.launch {
                delay(500)
                if (uiState.value.level < PuzzleImageSource.puzzles.size) {
                    _uiState.update {
                        it.copy(
                            level = uiState.value.level + 1,
                            puzzle = PuzzleImageSource.puzzles[uiState.value.level],
                            orientation = updateOrientation(
                                PuzzleImageSource.puzzles[uiState.value.level].size
                            )
                        )
                    }
                }
                nextLevel = false
                val isGenerated = (0..2).random() == 0
                if (isGenerated) {
                    updateOpenQuestionDialog(true)
                    question = QuestionsSource.questions[Random.nextInt(0, 13)]
                }
            }
        }
    }

    private fun updateOrientation(size: Int): List<MutableList<Int>>  {
        val orientation: MutableList<MutableList<Int>> = mutableListOf()
        for (i in 0 until size) {
            val line: MutableList<Int> = mutableListOf()
            for (j in 0 until size) {
                line.add((0..3).random() * 90)
            }
            orientation.add(line)
        }
        return orientation.toList()
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
                PuzzleGameViewModel(
                    offlineUserRepository = offlineUserRepository,
                    user = user
                )
            }
        }
    }
}