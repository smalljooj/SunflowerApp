package com.github.smalljooj.sunflowerapp.ui.loading

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.github.smalljooj.sunflowerapp.R
import com.github.smalljooj.sunflowerapp.SunflowerApplication
import com.github.smalljooj.sunflowerapp.data.repositories.UserRepository
import com.github.smalljooj.sunflowerapp.data.types.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoadingViewModel(
    private val userRepository: UserRepository,
    private val user: MutableStateFlow<User>
): ViewModel() {
    var isLoading: Boolean by mutableStateOf(true)
        private set

    fun verifySession(
        goToWelcomeScreen: () -> Unit,
        goToHomeScreen: () -> Unit
    ) {
        viewModelScope.launch {
            val users = userRepository.getAllUsers().first()
            isLoading = false

            if (users.isEmpty()) {
                goToWelcomeScreen()
            } else {
                user.update {
                    it.copy(
                        id = users.first().id,
                        name = users.first().name,
                        level = users.first().level,
                        image = users.first().image,
                        imageTitle = users.first().imageTitle,
                        profileId = users.first().profileId
                    )
                }
                goToHomeScreen()
            }
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as SunflowerApplication)
                val offlineUserRepository = application.container.offlineUserRepository
                val user = application.container.user
                LoadingViewModel(
                    userRepository = offlineUserRepository,
                    user = user
                )
            }
        }
    }

}