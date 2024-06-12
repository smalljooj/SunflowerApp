package com.github.smalljooj.sunflowerapp.ui.profile

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.github.smalljooj.sunflowerapp.SunflowerApplication
import com.github.smalljooj.sunflowerapp.data.types.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel(
    val user: MutableStateFlow<User>
): ViewModel() {

    private val _uiState = MutableStateFlow(
        ProfileUiState(
            image = user.value.image,
            title = user.value.imageTitle,
            name = user.value.name
        )
    )
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()
    var openDialog by mutableStateOf(false)
        private set

    fun updateOpenDialog(value: Boolean) {
        openDialog = value
    }
    fun updateImage(@DrawableRes image: Int) {
        _uiState.update {
            it.copy(
                image = image
            )
        }
    }
    fun updateTitle(@StringRes title: Int) {
        _uiState.update {
            it.copy(
                title = title
            )
        }
    }
    fun updateName(name: String) {
        _uiState.update {
            it.copy(
                name = name
            )
        }
    }
    fun insertUser() {
        updateUser()
    }
    fun updateUser() {
        user.update {
            it.copy(
                name = _uiState.value.name,
                image = _uiState.value.image,
                imageTitle = _uiState.value.title
            )
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as SunflowerApplication)
                val user = application.container.user
                ProfileViewModel(user =  user)
            }
        }
    }
}