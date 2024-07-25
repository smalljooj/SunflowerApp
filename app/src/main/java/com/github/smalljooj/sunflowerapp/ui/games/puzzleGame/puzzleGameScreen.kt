package com.github.smalljooj.sunflowerapp.ui.games.puzzleGame

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.smalljooj.sunflowerapp.R
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PuzzleGameScreen(
    goToHomeScreen: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PuzzleGameViewModel = viewModel(),
    context: Context = LocalContext.current
) {
    val uiState by viewModel.uiState.collectAsState()
    val onEvent: (PuzzleGameEvent) -> Unit = viewModel::onEvent

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxSize()
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
            if (viewModel.recomposition) {
                Column {
                    for (i in 0 until uiState.puzzle.size) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            for (j in 0 until uiState.puzzle.size) {
                                Image(
                                    painter = painterResource(id = uiState.puzzle[i][j].image),
                                    contentDescription = null,
                                    contentScale = ContentScale.FillWidth,
                                    modifier = Modifier
                                        .weight(1F)
                                        .border(BorderStroke(1.dp, Color.Black))
                                        .rotate(uiState.orientation[i][j].toFloat())
                                        .clickable {
                                            viewModel.rotateImage(i, j, context)
                                        }
                                )
                            }
                        }
                    }
                }
            } else {
                Column {
                    for (i in 0 until uiState.puzzle.size) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            for (j in 0 until uiState.puzzle.size) {
                                Image(
                                    painter = painterResource(id = uiState.puzzle[i][j].image),
                                    contentDescription = null,
                                    contentScale = ContentScale.FillWidth,
                                    modifier = Modifier
                                        .weight(1F)
                                        .border(BorderStroke(1.dp, Color.Black))
                                        .rotate(uiState.orientation[i][j].toFloat())
                                        .clickable {
                                            viewModel.rotateImage(i, j, context)
                                        }
                                )
                            }
                        }
                    }
                }
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
