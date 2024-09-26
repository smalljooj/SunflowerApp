package com.github.smalljooj.sunflowerapp.data.types.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ImageModel(
    @DrawableRes val image: Int,
    @StringRes val content: Int
)
