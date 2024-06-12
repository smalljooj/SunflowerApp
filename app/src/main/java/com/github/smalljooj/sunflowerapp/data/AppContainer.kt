package com.github.smalljooj.sunflowerapp.data

import android.content.Context
import com.github.smalljooj.sunflowerapp.data.types.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

interface AppContainer {

    val user: MutableStateFlow<User>
}

class DefaultAppContainer(
    private val context: Context
): AppContainer {

    override val user: MutableStateFlow<User> = MutableStateFlow(User())
}