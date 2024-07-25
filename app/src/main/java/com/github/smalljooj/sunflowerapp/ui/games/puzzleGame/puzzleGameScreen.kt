package com.github.smalljooj.sunflowerapp.ui.games.puzzleGame

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.smalljooj.sunflowerapp.R
import com.github.smalljooj.sunflowerapp.ui.theme.Custard
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PuzzleGameScreen(
    goToHomeScreen: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PuzzleGameViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val onEvent: (PuzzleGameEvent) -> Unit = viewModel::onEvent

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxSize()
                .padding(8.dp)
        ) {
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.level,uiState.level),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .padding(16.dp)
                )
            }
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(ratio = 1f)
                    .pointerInput(true) {
                        detectTapGestures {
                            //onEvent(SnakeGameEvent.UpdateDirection(it, size.width))
                        }
                    }
            ) {
                val cellSize = size.width / uiState.xAxisGridSize
                drawGameBoard(
                    cellSize = cellSize,
                    cellColor = Custard,
                    gridHeight = uiState.yAxisGridSize
                )
            }
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = { onEvent(PuzzleGameEvent.ResetGame) }
                ) {
                    Text(text = stringResource(id = R.string.restart))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = { goToHomeScreen() },
                ) {
                    Text(text = stringResource(id = R.string.back))
                }
            }
        }
    }
}
private fun DrawScope.drawGameBoard(
    cellSize: Float,
    cellColor: Color,
    gridHeight: Int
) {
    for (i in 0 until gridHeight) {
        for (j in 0 until gridHeight) {
            drawRect(
                color = if((i + j) % 2 == 0) cellColor
                else cellColor.copy(alpha = 0.5f),
                topLeft = Offset(x = i * cellSize, y = j * cellSize),
                size = Size(cellSize, cellSize)
            )
        }
    }
}
