package com.github.smalljooj.sunflowerapp.ui.games.puzzleGame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PuzzleGameViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(PuzzleGameUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: PuzzleGameEvent) {
        when(event) {
            PuzzleGameEvent.ResetGame -> {
                _uiState.value = PuzzleGameUiState()
            }
        }
    }
}