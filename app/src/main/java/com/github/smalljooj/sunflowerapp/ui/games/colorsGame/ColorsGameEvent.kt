package com.github.smalljooj.sunflowerapp.ui.games.colorsGame

sealed class ColorsGameEvent {
    data object ResetGame: ColorsGameEvent()
}