package com.github.smalljooj.sunflowerapp.ui.games.puzzleGame

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.smalljooj.sunflowerapp.R
import com.github.smalljooj.sunflowerapp.data.source.PuzzleImageSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PuzzleGameViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(PuzzleGameUiState())
    val uiState = _uiState.asStateFlow()
    var recomposition by mutableStateOf(true)
    private var nextLevel = false

    fun onEvent(event: PuzzleGameEvent) {
        when(event) {
            PuzzleGameEvent.ResetGame -> {
                _uiState.value = PuzzleGameUiState()
                recomposition = !recomposition
            }
        }
    }

    fun rotateImage(x: Int, y: Int, context: Context) {
        if (!nextLevel) {
            if (_uiState.value.orientation[x][y] < 270) {
                _uiState.value.orientation[x][y] += 90
                recomposition = !recomposition
            } else {
                _uiState.value.orientation[x][y] = 0
                recomposition = !recomposition
            }
            verifyGame(context)
        }
    }
    private fun verifyGame(context: Context) {
        nextLevel = true
        val winSound = MediaPlayer.create(context, R.raw.winsound)
        _uiState.value.orientation.forEach { it ->
           it.forEach{
               if (it != 0) {
                   nextLevel = false
               }
           }
       }
        if (nextLevel) {
            winSound.start()
            viewModelScope.launch {
                delay(500)
                if (uiState.value.level < PuzzleImageSource.puzzles.size) {
                    _uiState.update {
                        it.copy(
                            level = uiState.value.level + 1,
                            puzzle = PuzzleImageSource.puzzles[uiState.value.level],
                            orientation = updateOrientation(
                                PuzzleImageSource.puzzles[uiState.value.level].size
                            )
                        )
                    }
                }
                nextLevel = false
            }
        }
    }

    private fun updateOrientation(size: Int): List<MutableList<Int>>  {
        val orientation: MutableList<MutableList<Int>> = mutableListOf()
        for (i in 0 until size) {
            val line: MutableList<Int> = mutableListOf()
            for (j in 0 until size) {
                line.add((0..3).random() * 90)
            }
            orientation.add(line)
        }
        return orientation.toList()
    }
}