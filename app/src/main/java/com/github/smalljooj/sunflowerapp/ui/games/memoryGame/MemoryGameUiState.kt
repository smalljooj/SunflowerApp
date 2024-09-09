package com.github.smalljooj.sunflowerapp.ui.games.memoryGame

import com.github.smalljooj.sunflowerapp.data.types.MemoryCard

data class MemoryGameUiState(
    val level: Int = 1,
    val cards: List<List<MemoryCard>> = emptyList(),
)

