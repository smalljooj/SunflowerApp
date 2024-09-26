package com.github.smalljooj.sunflowerapp.ui.games.snakeGame

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.github.smalljooj.sunflowerapp.SunflowerApplication
import com.github.smalljooj.sunflowerapp.data.repositories.UserRepository
import com.github.smalljooj.sunflowerapp.data.source.QuestionsSource
import com.github.smalljooj.sunflowerapp.data.types.model.User
import com.github.smalljooj.sunflowerapp.data.util.answerQuestion
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class SnakeGameViewModel(
    private val offlineUserRepository: UserRepository,
    val user: MutableStateFlow<User>
): ViewModel() {
    private val _uiState = MutableStateFlow(SnakeGameUiState())
    val uiState: StateFlow<SnakeGameUiState> = _uiState.asStateFlow()
    var color by mutableStateOf(Color(0xFF059212))
    var openQuestionDialog by mutableStateOf(false)
        private set
    var question = QuestionsSource.questions[Random.nextInt(0, 13)]

    fun updateOpenQuestionDialog(value: Boolean) {
        openQuestionDialog = value
    }

    fun onEvent(event: SnakeGameEvent) {
        when(event) {
            SnakeGameEvent.PauseGame -> {
                _uiState.update { it.copy(gameState = GameState.PAUSED) }
            }
            SnakeGameEvent.ResetGame -> {
                _uiState.value = SnakeGameUiState()
            }
            SnakeGameEvent.StartGame -> {
                _uiState.update { it.copy(gameState = GameState.STARTED) }
                viewModelScope.launch {
                    while (uiState.value.gameState == GameState.STARTED) {
                        val delayMillis = when(_uiState.value.snake.size) {
                            in 1..5 -> 120L
                            in 6..10 -> 110L
                            else -> 100L
                        }
                        delay(delayMillis)
                        _uiState.value = updateGame(uiState.value)
                    }
                }
            }
            is SnakeGameEvent.UpdateDirection -> {
                updateDirection(event.offset, event.canvasWidth)
            }
        }
    }

    private fun updateDirection(offset: Offset, canvasWidth: Int) {
        if (!uiState.value.isGameOver) {
            val cellSize = canvasWidth / _uiState.value.xAxisGridSize
            val tapX = (offset.x / cellSize).toInt()
            val tapY = (offset.y / cellSize).toInt()
            val head = uiState.value.snake.first()

            _uiState.update {
                it.copy(
                    direction = when(uiState.value.direction) {
                        Direction.UP, Direction.DOWN ->
                           if (tapX < head.x) Direction.LEFT else Direction.RIGHT
                        Direction.LEFT, Direction.RIGHT ->
                            if (tapY < head.y) Direction.UP else Direction.DOWN
                    }
                )
            }
        }

    }

    private fun updateGame(currentGame: SnakeGameUiState): SnakeGameUiState{
        val colors: List<Color> = listOf(
            Color(0xFF059212),
            Color(0xFFEB5B00),
            Color(0xFFB60071),
            Color(0xFFE4003A),
            Color(0xFF00215E),
            Color(0xFF2C4E80),
            Color(0xFF1A5319),
        )
        if (currentGame.isGameOver) {
            return currentGame
        }
        val head = currentGame.snake.first()
        val xAxisGridSize = currentGame.xAxisGridSize
        val yAxisGridSize = currentGame.yAxisGridSize
        val newHead = when(currentGame.direction) {
            Direction.UP -> {
                Coordinate(x = head.x, y = head.y - 1)
            }
            Direction.DOWN -> {
                Coordinate(x = head.x, y = head.y + 1)
            }
            Direction.LEFT -> {
                Coordinate(x = head.x - 1, y = head.y)
            }
            Direction.RIGHT -> {
                Coordinate(x = head.x + 1, y = head.y)
            }
        }
        if (currentGame.snake.contains(newHead) ||
            !isWithinBounds(newHead, xAxisGridSize, yAxisGridSize)
            ) {
            updateOpenQuestionDialog((0..2).random() == 0)
            question = QuestionsSource.questions[Random.nextInt(0, 13)]
            return currentGame.copy(isGameOver = true)
        }
        var newSnake = mutableListOf(newHead) + currentGame.snake
        val newFood = if (newHead == currentGame.food) SnakeGameUiState.generateRandomFoodCoordinate()
        else currentGame.food
        if (newHead == currentGame.food) {
            color = colors[Random.nextInt(0, 7)]
        }
        if (newHead != currentGame.food) {
            newSnake = newSnake.toMutableList()
            newSnake.removeAt(newSnake.size - 1)
        }
        return currentGame.copy(snake = newSnake, food = newFood)
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

    private fun isWithinBounds(
        coordinate: Coordinate,
        xAxisGridSize: Int,
        yAxisGridSize: Int,
    ): Boolean {
        return coordinate.x in 1 until xAxisGridSize - 1
                && coordinate.y in 1 until yAxisGridSize - 1
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as SunflowerApplication)
                val offlineUserRepository = application.container.offlineUserRepository
                val user = application.container.user
                SnakeGameViewModel(
                    offlineUserRepository = offlineUserRepository,
                    user = user
                )
            }
        }
    }
}