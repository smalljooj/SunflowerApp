package com.github.smalljooj.sunflowerapp.ui.games.colorsGame

import androidx.compose.ui.graphics.Color
import com.github.smalljooj.sunflowerapp.data.types.model.CircleColor

data class ColorsGameUiState(
    val level: Int = 1,
    val colorsRef: List<CircleColor> = emptyList(),
    val colorsGuest: MutableList<CircleColor> =
        mutableListOf(
            CircleColor(color = Color.Gray, index = 0),
            CircleColor(color = Color.Gray, index = 1),
            CircleColor(color = Color.Gray, index = 2),
        )
)
