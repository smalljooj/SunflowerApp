package com.github.smalljooj.sunflowerapp.ui.games.snakeGame

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.smalljooj.sunflowerapp.R
import com.github.smalljooj.sunflowerapp.ui.theme.Citrine
import com.github.smalljooj.sunflowerapp.ui.theme.Custard
import com.github.smalljooj.sunflowerapp.ui.theme.RoyalBlue

@Composable
fun SnakeGameScreen(
    goToHome: () -> Unit,
    viewModel: SnakeGameViewModel = viewModel(),
    context: Context = LocalContext.current
) {
    val onEvent: (SnakeGameEvent) -> Unit = viewModel::onEvent
    val state by viewModel.uiState.collectAsState()
    val foodImageBitmap = ImageBitmap.imageResource(id = R.drawable.img_apple)
    val snakeHeadImage = ImageBitmap.imageResource(
        when(state.direction){
            Direction.UP -> R.drawable.img_snake_head3
            Direction.DOWN -> R.drawable.img_snake_head4
            Direction.LEFT -> R.drawable.img_snake_head2
            Direction.RIGHT -> R.drawable.img_snake_head
        }
    )
    val foodSoundMP = remember { MediaPlayer.create(context, R.raw.food) }
    val gameOverSoundMP = remember { MediaPlayer.create(context, R.raw.gameover) }
    LaunchedEffect(key1 = state.snake.size) {
        if (state.snake.size != 1) {
            foodSoundMP?.start()
        }
    }
    LaunchedEffect(key1 = state.isGameOver) {
        if (state.snake.size != 1) {
            gameOverSoundMP?.start()
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxSize()
        ) {
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Score: ${state.snake.size - 1}",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .padding(16.dp)
                )
            }
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(ratio = 2 / 3f)
                    .pointerInput(state.gameState) {
                        if (state.gameState != GameState.STARTED) {
                            return@pointerInput
                        }
                        detectTapGestures {
                            onEvent(SnakeGameEvent.UpdateDirection(it, size.width))
                        }
                    }
            ) {
                val cellSize = size.width / 20
                drawGameBoard(
                    cellSize = cellSize,
                    cellColor = Custard,
                    borderCellColor = RoyalBlue,
                    gridWidth = state.xAxisGridSize,
                    gridHeight = state.yAxisGridSize
                )
                drawFood(
                    foodImage = foodImageBitmap,
                    cellSize = cellSize.toInt(),
                    coordinate = state.food
                )
                drawSnake(
                    snakeHeadImage = snakeHeadImage,
                    cellSize = cellSize,
                    snake = state.snake
                )
            }
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = { onEvent(SnakeGameEvent.ResetGame) },
                    enabled = state.gameState == GameState.PAUSED || state.isGameOver
                ) {
                    Text(text = "Restart")
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        when(state.gameState) {
                            GameState.IDLE, GameState.PAUSED -> onEvent(SnakeGameEvent.StartGame)
                            GameState.STARTED -> onEvent(SnakeGameEvent.PauseGame)
                        }
                    },
                    enabled = !state.isGameOver
                ) {
                    Text(text = when(state.gameState) {
                        GameState.IDLE -> "Start"
                        GameState.STARTED -> "Pause"
                        GameState.PAUSED -> "Resume"
                    })
                }
            }
        }
        AnimatedVisibility(visible = state.isGameOver) {
           Text(
               text = "Game Over",
               style = MaterialTheme.typography.displayMedium,
               modifier = Modifier.padding(16.dp)
           )
        }
    }
}

private fun DrawScope.drawGameBoard(
    cellSize: Float,
    cellColor: Color,
    borderCellColor: Color,
    gridWidth: Int,
    gridHeight: Int
) {
    for (i in 0 until gridHeight) {
        for (j in 0 until gridHeight) {
            val isBorderCell = i == 0 || j == 0 || i == gridWidth - 1 || j == gridHeight - 1
            drawRect(
                color = if(isBorderCell) borderCellColor
                else if((i + j) % 2 == 0) cellColor
                else cellColor.copy(alpha = 0.5f),
                topLeft = Offset(x = i * cellSize, y = j * cellSize),
                size = Size(cellSize, cellSize)
            )
        }
    }

}

private fun DrawScope.drawFood(
    foodImage: ImageBitmap,
    cellSize: Int,
    coordinate: Coordinate
) {
    drawImage(
        image = foodImage,
        dstOffset = IntOffset(
            x = coordinate.x * cellSize,
            y = coordinate.y * cellSize
        ),
        dstSize = IntSize(cellSize, cellSize)
    )
}

private fun DrawScope.drawSnake(
    snakeHeadImage: ImageBitmap,
    cellSize: Float,
    snake: List<Coordinate>
) {
    val cellSizeInt = cellSize.toInt()
    snake.forEachIndexed { index, coordinate ->
        val radius = if (index == snake.lastIndex) cellSize / 2.5f else cellSize / 2
        if(index == 0) {
            drawImage(
                image = snakeHeadImage,
                dstOffset = IntOffset(
                    x = coordinate.x * cellSizeInt,
                    y = coordinate.y * cellSizeInt
                ),
                dstSize = IntSize(cellSizeInt, cellSizeInt)
            )
        } else {
            drawCircle(
                color = Citrine,
                center = Offset(
                    x = coordinate.x * cellSize + radius,
                    y = coordinate.y * cellSize + radius
                ),
                radius = radius
            )

        }

    }
}