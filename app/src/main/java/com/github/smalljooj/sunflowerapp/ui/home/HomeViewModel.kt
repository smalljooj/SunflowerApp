package com.github.smalljooj.sunflowerapp.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import co.yml.charts.common.model.Point
import co.yml.charts.ui.barchart.models.BarData
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
    var barsData: List<BarData> = emptyList()

    init {
        updateChart()
        updateOpenQuestionDialog(Random.nextInt(0, 2) == 0)
    }

    private fun updateChart() {
        barsData = listOf(
            BarData(Point(x = 0f, y = 0f), label = "", color = Color.Red),
            BarData(Point(x = 1f, y = user.value.anger.toFloat()), label = "", color = Color.Red),
            BarData(Point(x = 2f, y = user.value.anxiety.toFloat()), label = "", color = Color.Yellow),
            BarData(Point(x = 3f, y = user.value.sadness.toFloat()), label = "", color = Color.Gray),
            BarData(Point(x = 4f, y = user.value.happiness.toFloat()), label = "", color = Color.Blue),
            BarData(Point(x = 5f, y = user.value.jealousy.toFloat()), label = "", color = Color.Magenta),
            BarData(Point(x = 6f, y = user.value.disgust.toFloat()), label = "", color = Color.Green),
        )
    }

    fun updateOpenDialog(value: Boolean) {
        updateChart()
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
            updateChart()
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