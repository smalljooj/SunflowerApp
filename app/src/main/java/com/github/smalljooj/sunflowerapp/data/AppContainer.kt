package com.github.smalljooj.sunflowerapp.data

import android.content.Context
import com.github.smalljooj.sunflowerapp.data.database.SunflowerDatabase
import com.github.smalljooj.sunflowerapp.data.repositories.OfflineUserRepository
import com.github.smalljooj.sunflowerapp.data.repositories.UserRepository
import com.github.smalljooj.sunflowerapp.data.types.model.User
import kotlinx.coroutines.flow.MutableStateFlow

interface AppContainer {
    val offlineUserRepository: UserRepository
    val user: MutableStateFlow<User>
}

class DefaultAppContainer(
    private val context: Context
): AppContainer {
    override val offlineUserRepository: UserRepository by lazy {
        OfflineUserRepository(SunflowerDatabase.getDatabase(context).userDao())
    }
    override val user: MutableStateFlow<User> = MutableStateFlow(User())
}