package com.github.smalljooj.sunflowerapp.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.github.smalljooj.sunflowerapp.SunflowerApplication
import com.github.smalljooj.sunflowerapp.data.repositories.UserRepository
import com.github.smalljooj.sunflowerapp.data.source.QuestionsSource
import com.github.smalljooj.sunflowerapp.data.types.model.User
import com.github.smalljooj.sunflowerapp.data.util.answerQuestion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class HomeViewModel(
    val userState: StateFlow<User>,
    private val offlineUserRepository: UserRepository,
    private val user: MutableStateFlow<User>
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

    fun answer(answer: Boolean) {
        viewModelScope.launch {
            answerQuestion(
                user = user,
                offlineUserRepository = offlineUserRepository,
                question = question,
                answer = answer
            )
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as SunflowerApplication)
                val offlineUserRepository = application.container.offlineUserRepository
                val user = application.container.user
                HomeViewModel(
                    userState = user.asStateFlow(),
                    user = user,
                    offlineUserRepository = offlineUserRepository
                )
            }
        }
    }
}