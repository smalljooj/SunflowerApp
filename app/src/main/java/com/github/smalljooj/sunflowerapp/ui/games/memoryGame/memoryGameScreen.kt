package com.github.smalljooj.sunflowerapp.ui.games.memoryGame

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.smalljooj.sunflowerapp.R

@Composable
fun MemoryGameScreen(
    goToHomeScreen: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MemoryGameViewModel = viewModel(factory = MemoryGameViewModel.Factory),
    context: Context = LocalContext.current
) {

    val uiState by viewModel.uiState.collectAsState()
    val onEvent: (MemoryGameEvent) -> Unit = viewModel::onEvent
    //val screenWidth = LocalConfiguration.current.screenWidthDp

    Column {
        Card(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.level, uiState.level),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(16.dp)
            )
        }
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Column {
                for (i in 0 until uiState.cards.size) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        for (j in 0 until uiState.cards[i].size) {
                            FlipCard(
                                frontContent = {
                                    Image(
                                        painter = painterResource(id = uiState.cards[i][j].image),
                                        contentDescription = stringResource(id = R.string.front_card),
                                        contentScale = ContentScale.FillWidth,
                                    )
                                },
                                backContent = {
                                    Image(
                                        painter = painterResource(id = R.drawable.cardback),
                                        contentDescription = stringResource(id = R.string.back_card),
                                        contentScale = ContentScale.FillWidth,
                                    )
                                },
                                modifier = Modifier.weight(1f),
                                viewModel = viewModel,
                                positionX = i,
                                positionY = j
                            )
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
                    onClick = { onEvent(MemoryGameEvent.ResetGame) }
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
