package com.github.smalljooj.sunflowerapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.github.smalljooj.sunflowerapp.R

val josefinSlabFont = FontFamily(
    Font(R.font.josefinslab_medium),
    Font(R.font.josefinslab_light, weight = FontWeight.Light),
    Font(R.font.josefinslab_bold, weight = FontWeight.Bold),
    Font(R.font.josefinslab_italic, weight = FontWeight.Thin),
    Font(R.font.josefinslab_regular, weight = FontWeight.Normal),
    Font(R.font.josefinslab_bolditalic, weight = FontWeight.ExtraBold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    labelMedium = TextStyle(
        fontFamily = josefinSlabFont,
        color = Color.Black,
        fontSize = 26.sp
    )
)