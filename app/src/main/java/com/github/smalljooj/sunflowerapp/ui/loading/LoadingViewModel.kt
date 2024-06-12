package com.github.smalljooj.sunflowerapp.ui.loading

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoadingViewModel: ViewModel() {
    var isLoading: Boolean by mutableStateOf(true)
        private set

    fun verifySession(
        goToWelcomeScreen: () -> Unit,
        goToHomeScreen: () -> Unit
    ) {
        viewModelScope.launch {

        }
    }

}