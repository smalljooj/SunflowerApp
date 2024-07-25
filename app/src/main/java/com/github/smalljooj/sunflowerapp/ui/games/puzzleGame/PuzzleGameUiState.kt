package com.github.smalljooj.sunflowerapp.ui.games.puzzleGame

data class PuzzleGameUiState(
    val level: Int = 1,
    val puzzle: List<Int> = emptyList(),
    val xAxisGridSize: Int = 3,
    val yAxisGridSize: Int = 3,
)
