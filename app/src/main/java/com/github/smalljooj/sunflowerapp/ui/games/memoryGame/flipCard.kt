package com.github.smalljooj.sunflowerapp.ui.games.memoryGame

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun FlipCard(
    frontContent: @Composable () -> Unit,
    backContent: @Composable () -> Unit,
    positionX: Int,
    positionY: Int,
    modifier: Modifier = Modifier,
    viewModel: MemoryGameViewModel
) {
    var rotated by rememberSaveable { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        targetValue = if (rotated) 180f else 0f,
        animationSpec = tween(500), label = ""
    )
    val animateFront by animateFloatAsState(
        targetValue = if (!rotated) 1f else 0f,
        animationSpec = tween(500), label = ""
    )
    val animateBack by animateFloatAsState(
        targetValue = if (rotated) 1f else 0f,
        animationSpec = tween(500), label = ""
    )
    val animateColor by animateColorAsState(
        targetValue = if (rotated) Color(0xFFD3D3D3) else Color(0xFF808080),
        animationSpec = tween(500), label = ""
    )

    LaunchedEffect(viewModel.recomposition) {
        rotated = viewModel.checkIsTurned(positionX, positionY)
    }

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        modifier = modifier
            .padding(all = 5.dp)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 8 * density
            }
            .clickable {
                if (!rotated) {
                    rotated = true
                    viewModel.flipCard(true, positionX, positionY)
                }
            }
            .dashedBorder(
            color = Color.Black,
            shape = RoundedCornerShape(8.dp),
            dashLength = 10.dp,
            gapLength = 10.dp
            ),
        colors = CardDefaults.cardColors(
            containerColor = animateColor,
        ),
    )
    {
        Row(
            modifier = Modifier
                .padding(all = 2.dp)
                .graphicsLayer {
                    alpha = if (rotated) animateBack else animateFront
                    rotationY = rotation
                },
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (rotated) {
                CardContent(frontContent)
            } else {
                CardContent(backContent)
            }
        }
    }
}

@Composable
fun CardContent(content: @Composable () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        content()
    }
}

fun Modifier.dashedBorder(
    color: Color,
    shape: Shape,
    strokeWidth: Dp = 2.dp,
    dashLength: Dp = 4.dp,
    gapLength: Dp = 4.dp,
    cap: StrokeCap = StrokeCap.Round
) = dashedBorder(brush = SolidColor(color), shape, strokeWidth, dashLength, gapLength, cap)

fun Modifier.dashedBorder(
    brush: Brush,
    shape: Shape,
    strokeWidth: Dp = 2.dp,
    dashLength: Dp = 4.dp,
    gapLength: Dp = 4.dp,
    cap: StrokeCap = StrokeCap.Round
) = this.drawWithContent {

    val outline = shape.createOutline(size, layoutDirection, density = this)

    val dashedStroke = Stroke(
        cap = cap,
        width = strokeWidth.toPx(),
        pathEffect = PathEffect.dashPathEffect(
            intervals = floatArrayOf(dashLength.toPx(), gapLength.toPx())
        )
    )

    drawContent()

    drawOutline(
        outline = outline,
        style = dashedStroke,
        brush = brush
    )
}
