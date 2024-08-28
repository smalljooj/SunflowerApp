package com.github.smalljooj.sunflowerapp.data.types

import androidx.annotation.DrawableRes

data class MemoryCard(
    @DrawableRes val image: Int,
    val turned: Boolean
)