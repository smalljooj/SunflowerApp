package com.github.smalljooj.sunflowerapp.ui.welcome

import com.github.smalljooj.sunflowerapp.R

class OnBoardingItems(
    val image: Int,
    val title: Int,
) {
    companion object {
        fun getData(): List<OnBoardingItems> {
            return listOf(
                OnBoardingItems(R.drawable.welcome1, R.string.welcome_message_1),
                OnBoardingItems(R.drawable.welcome2, R.string.welcome_message_2),
                OnBoardingItems(R.drawable.welcome3, R.string.welcome_message_3)
            )
        }
    }
}
