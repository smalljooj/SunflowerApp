package com.github.smalljooj.sunflowerapp.data.types

import androidx.annotation.DrawableRes
import com.github.smalljooj.sunflowerapp.R

data class MemoryCard(
    @DrawableRes var image: Int = R.drawable.girafa,
    var isTurned: Boolean = false
)