package com.github.smalljooj.sunflowerapp.ui.profile

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.github.smalljooj.sunflowerapp.R

data class ProfileUiState(
    @DrawableRes val image: Int = R.drawable.avatar_1,
    @StringRes val title: Int = R.string.icon_lion,
    val name: String = ""
)
