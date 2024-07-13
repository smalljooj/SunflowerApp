package com.github.smalljooj.sunflowerapp.ui.games.snakeGame

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SnakeGameViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(SnakeGameUiState())
    val uiState: StateFlow<SnakeGameUiState> = _uiState.asStateFlow()

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
            return currentGame.copy(isGameOver = true)
        }
        var newSnake = mutableListOf(newHead) + currentGame.snake
        val newFood = if (newHead == currentGame.food) SnakeGameUiState.generateRandomFoodCoordinate()
        else currentGame.food
        if (newHead != currentGame.food) {
            newSnake = newSnake.toMutableList()
            newSnake.removeAt(newSnake.size - 1)
        }
        return currentGame.copy(snake = newSnake, food = newFood)
    }

    private fun isWithinBounds(
        coordinate: Coordinate,
        xAxisGridSize: Int,
        yAxisGridSize: Int,
    ): Boolean {
        return coordinate.x in 1 until xAxisGridSize - 1
                && coordinate.y in 1 until yAxisGridSize - 1
    }

}