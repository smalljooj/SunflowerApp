package com.github.smalljooj.sunflowerapp.ui.games.memoryGame

sealed class MemoryGameEvent {
    data object ResetGame: MemoryGameEvent()
}