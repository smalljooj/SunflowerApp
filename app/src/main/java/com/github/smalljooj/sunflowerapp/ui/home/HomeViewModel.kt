package com.github.smalljooj.sunflowerapp.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.github.smalljooj.sunflowerapp.SunflowerApplication
import com.github.smalljooj.sunflowerapp.data.source.QuestionsSource
import com.github.smalljooj.sunflowerapp.data.types.model.User
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random

class HomeViewModel(
    val userState: StateFlow<User>
): ViewModel() {
    var openDialog by mutableStateOf(false)
        private set
    var openQuestionDialog by mutableStateOf(false)
        private set
    val question = QuestionsSource.questions[Random.nextInt(0, 13)]

    init {
        updateOpenQuestionDialog(Random.nextInt(0, 2) == 0)
    }

    fun updateOpenDialog(value: Boolean) {
        openDialog = value
    }

    fun updateOpenQuestionDialog(value: Boolean) {
        openQuestionDialog = value
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as SunflowerApplication)
                val userState = application.container.user
                HomeViewModel(
                    userState = userState.asStateFlow()
                )
            }
        }
    }
}