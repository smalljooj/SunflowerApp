package com.github.smalljooj.sunflowerapp.ui.games.memoryGame

import androidx.compose.ui.graphics.Color
import com.github.smalljooj.sunflowerapp.data.types.MemoryCard
import com.github.smalljooj.sunflowerapp.data.types.model.CircleColor

data class MemoryGameUiState(
    val level: Int = 1,
    val cards: List<List<MemoryCard>> = emptyList(),
)

