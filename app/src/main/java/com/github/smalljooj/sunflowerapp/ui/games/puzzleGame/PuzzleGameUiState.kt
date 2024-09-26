package com.github.smalljooj.sunflowerapp.ui.games.puzzleGame

import com.github.smalljooj.sunflowerapp.data.source.PuzzleImageSource
import com.github.smalljooj.sunflowerapp.data.types.model.ImagePuzzle

data class PuzzleGameUiState(
    val level: Int = 1,
    val puzzle: List<List<ImagePuzzle>> = PuzzleImageSource.puzzles[0].toList(),
    val orientation: List<MutableList<Int>> = getOrientation()
)

private fun getOrientation(): List<MutableList<Int>>  {
    val orientation: MutableList<MutableList<Int>> = mutableListOf()
    for (i in 0 until 2) {
        val line: MutableList<Int> = mutableListOf()
        for (j in 0 until 2) {
            line.add((0..3).random() * 90)
        }
        orientation.add(line)
    }
    return orientation.toList()
}
