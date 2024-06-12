package com.github.smalljooj.sunflowerapp.ui.profile

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
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
    fun insertUser(goToHomeScreen: () -> Unit) {
        goToHomeScreen()
    }
}