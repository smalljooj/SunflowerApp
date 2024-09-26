package com.github.smalljooj.sunflowerapp.ui.games.puzzleGame

sealed class PuzzleGameEvent {
    data object ResetGame: PuzzleGameEvent()
}