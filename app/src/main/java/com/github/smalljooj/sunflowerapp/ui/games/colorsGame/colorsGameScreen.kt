package com.github.smalljooj.sunflowerapp.ui.games.colorsGame

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.smalljooj.sunflowerapp.R
import com.github.smalljooj.sunflowerapp.data.source.ColorsSource
import com.github.smalljooj.sunflowerapp.data.types.model.CircleColor

@Composable
fun ColorsGameScreen(
    goToHomeScreen: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ColorsGameViewModel = viewModel(factory = ColorsGameViewModel.Factory),
    context: Context = LocalContext.current
) {
    val uiState by viewModel.uiState.collectAsState()
    val onEvent: (ColorsGameEvent) -> Unit = viewModel::onEvent
    //val screenWidth = LocalConfiguration.current.screenWidthDp

    DraggableScreen(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Column {
            Card(
                modifier = Modifier
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
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AnimatedVisibility(
                        viewModel.isMemorizeTime,
                        enter = slideInHorizontally (initialOffsetX = {it})
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .background(
                                    Color(0xFFEE6C4D),
                                    RoundedCornerShape(40.dp)
                                ),
                        ) {
                            Column (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp)
                                    .background(
                                        Color.White,
                                        RoundedCornerShape(40.dp)
                                    ),
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    for (i in 0..2) {
                                        Box(
                                            modifier = Modifier
                                                .size(80.dp)
                                                .clip(RoundedCornerShape(40.dp))
                                                .shadow(5.dp, RoundedCornerShape(40.dp))
                                                .background(
                                                    uiState.colorsRef[i].color,
                                                    RoundedCornerShape(40.dp)
                                                )
                                        )
                                    }
                                }
                                if (uiState.colorsRef.size > 3) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceEvenly
                                    ) {
                                        for (i in 3 until uiState.colorsRef.size) {
                                            Box(
                                                modifier = Modifier
                                                    .size(80.dp)
                                                    .clip(RoundedCornerShape(40.dp))
                                                    .shadow(5.dp, RoundedCornerShape(40.dp))
                                                    .background(
                                                        uiState.colorsRef[i].color,
                                                        RoundedCornerShape(40.dp)
                                                    )
                                            )
                                        }
                                    }
                                }
                            }
                            Text(
                                text = viewModel.time.toString(),
                                fontSize = 120.sp,
                                style = MaterialTheme.typography.headlineMedium,
                                color = Color.White
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                    if (!viewModel.isMemorizeTime) {
                            if (viewModel.recomposition) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    for (i in 0..2) {
                                        DropItem<CircleColor>(
                                            modifier = Modifier
                                                .size(80.dp)
                                                .clip(RoundedCornerShape(40.dp))
                                                .shadow(5.dp, RoundedCornerShape(40.dp))
                                        ) { isInBound, circle ->
                                            if (circle != null) {
                                                LaunchedEffect(key1 = circle) {
                                                    viewModel.updateColor(
                                                        circle.color,
                                                        uiState.colorsGuest[i].index,
                                                        context
                                                    )
                                                }
                                            }
                                            if (isInBound) {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .border(
                                                            1.dp,
                                                            color = Color.Red,
                                                            shape = RoundedCornerShape(40.dp)
                                                        )
                                                        .background(
                                                            uiState.colorsGuest[i].color,
                                                            RoundedCornerShape(40.dp)
                                                        )
                                                )
                                            } else {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .border(
                                                            1.dp,
                                                            color = Color.White,
                                                            shape = RoundedCornerShape(40.dp)
                                                        )
                                                        .background(
                                                            uiState.colorsGuest[i].color,
                                                            RoundedCornerShape(40.dp)
                                                        )
                                                )
                                            }
                                        }
                                    }
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    for (i in 3 until uiState.colorsGuest.size) {
                                        DropItem<CircleColor>(
                                            modifier = Modifier
                                                .size(80.dp)
                                                .clip(RoundedCornerShape(40.dp))
                                                .shadow(5.dp, RoundedCornerShape(40.dp))
                                        ) { isInBound, circle ->
                                            if (circle != null) {
                                                LaunchedEffect(key1 = circle) {
                                                    viewModel.updateColor(
                                                        circle.color,
                                                        uiState.colorsGuest[i].index,
                                                        context
                                                    )
                                                }
                                            }
                                            if (isInBound) {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .border(
                                                            1.dp,
                                                            color = Color.Red,
                                                            shape = RoundedCornerShape(40.dp)
                                                        )
                                                        .background(
                                                            uiState.colorsGuest[i].color,
                                                            RoundedCornerShape(40.dp)
                                                        )
                                                )
                                            } else {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .border(
                                                            1.dp,
                                                            color = Color.White,
                                                            shape = RoundedCornerShape(40.dp)
                                                        )
                                                        .background(
                                                            uiState.colorsGuest[i].color,
                                                            RoundedCornerShape(40.dp)
                                                        )
                                                )
                                            }
                                        }
                                    }
                                }
                            } else {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    for (i in 0..2) {
                                        DropItem<CircleColor>(
                                            modifier = Modifier
                                                .size(80.dp)
                                                .clip(RoundedCornerShape(40.dp))
                                                .shadow(5.dp, RoundedCornerShape(40.dp))
                                        ) { isInBound, circle ->
                                            if (circle != null) {
                                                LaunchedEffect(key1 = circle) {
                                                    viewModel.updateColor(
                                                        circle.color,
                                                        uiState.colorsGuest[i].index,
                                                        context
                                                    )
                                                }
                                            }
                                            if (isInBound) {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .border(
                                                            1.dp,
                                                            color = Color.Red,
                                                            shape = RoundedCornerShape(40.dp)
                                                        )
                                                        .background(
                                                            uiState.colorsGuest[i].color,
                                                            RoundedCornerShape(40.dp)
                                                        )
                                                )
                                            } else {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .border(
                                                            1.dp,
                                                            color = Color.White,
                                                            shape = RoundedCornerShape(40.dp)
                                                        )
                                                        .background(
                                                            uiState.colorsGuest[i].color,
                                                            RoundedCornerShape(40.dp)
                                                        )
                                                )
                                            }
                                        }
                                    }
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    for (i in 3 until uiState.colorsGuest.size) {
                                        DropItem<CircleColor>(
                                            modifier = Modifier
                                                .size(80.dp)
                                                .clip(RoundedCornerShape(40.dp))
                                                .shadow(5.dp, RoundedCornerShape(40.dp))
                                        ) { isInBound, circle ->
                                            if (circle != null) {
                                                LaunchedEffect(key1 = circle) {
                                                    viewModel.updateColor(
                                                        circle.color,
                                                        uiState.colorsGuest[i].index,
                                                        context
                                                    )
                                                }
                                            }
                                            if (isInBound) {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .border(
                                                            1.dp,
                                                            color = Color.Red,
                                                            shape = RoundedCornerShape(40.dp)
                                                        )
                                                        .background(
                                                            uiState.colorsGuest[i].color,
                                                            RoundedCornerShape(40.dp)
                                                        )
                                                )
                                            } else {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .border(
                                                            1.dp,
                                                            color = Color.White,
                                                            shape = RoundedCornerShape(40.dp)
                                                        )
                                                        .background(
                                                            uiState.colorsGuest[i].color,
                                                            RoundedCornerShape(40.dp)
                                                        )
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        (0..4).forEach {
                            DragTarget(
                                dataToDrop = ColorsSource.colors[it],
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(RoundedCornerShape(30.dp))
                                        .shadow(5.dp, RoundedCornerShape(30.dp))
                                        .background(
                                            ColorsSource.colors[it].color,
                                            RoundedCornerShape(30.dp)
                                        )
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        (5..9).forEach {
                            DragTarget(
                                dataToDrop = ColorsSource.colors[it],
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(RoundedCornerShape(30.dp))
                                        .shadow(5.dp, RoundedCornerShape(30.dp))
                                        .background(
                                            ColorsSource.colors[it].color,
                                            RoundedCornerShape(30.dp)
                                        )
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
                        onClick = { onEvent(ColorsGameEvent.ResetGame) }
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
}