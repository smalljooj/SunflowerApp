package br.edu.ifpb.sunflower.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

class ImageModel(
    @DrawableRes val image: Int,
    @StringRes val content: Int
) {
}