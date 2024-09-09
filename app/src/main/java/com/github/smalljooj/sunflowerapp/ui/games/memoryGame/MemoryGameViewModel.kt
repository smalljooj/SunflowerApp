package com.github.smalljooj.sunflowerapp.ui.games.memoryGame

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.github.smalljooj.sunflowerapp.SunflowerApplication
import com.github.smalljooj.sunflowerapp.data.repositories.UserRepository
import com.github.smalljooj.sunflowerapp.data.source.MemoryCardsSource
import com.github.smalljooj.sunflowerapp.data.source.QuestionsSource
import com.github.smalljooj.sunflowerapp.data.types.MemoryCard
import com.github.smalljooj.sunflowerapp.data.types.model.User
import com.github.smalljooj.sunflowerapp.data.util.answerQuestion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class MemoryGameViewModel(
    private val offlineUserRepository: UserRepository,
    val user: MutableStateFlow<User>
): ViewModel() {
    private val _uiState = MutableStateFlow(MemoryGameUiState())
    val uiState = _uiState.asStateFlow()
    var openQuestionDialog by mutableStateOf(false)
        private set
    var question = QuestionsSource.questions[Random.nextInt(0, 13)]
    var isMemorizeTime by mutableStateOf(true)
        private set
    var time by mutableIntStateOf(5)
        private set
    private val levels = listOf(
        listOf(2,2),
        listOf(3,2),
        listOf(4,2),
        listOf(4,3),
        listOf(4,4),
        listOf(5,4),
    )
    var recomposition by mutableStateOf(true)

    init {
        _uiState.update {
            it.copy(
                cards = getCards()
            )
        }
    }

    private suspend fun levelInit() {
    }

    private fun nextLevel() {

    }

    fun checkIsTurned(x: Int, y: Int): Boolean {
        return _uiState.value.cards[x][y].isTurned
    }

    private fun reset() {

    }

    private fun getCards(): List<List<MemoryCard>> {
        val maxLevel = if (uiState.value.level < 6) uiState.value.level - 1 else 5
        val level = levels[maxLevel]

        val cards = List(size = level[0]) {
            List(size = level[1]) {
                MemoryCard()
            }
        }
        val randomImages = (0..9).shuffled().take((level[0] * level[1]) / 2)
        var cardCount = 0

        val randomLines = (0 until level[0]).shuffled().take(level[0])
        for (i in randomLines) {
            val randomCols = (0 until level[1]).shuffled().take(level[1])
            for (j in randomCols) {
                cards[i][j].isTurned = false
                cards[i][j].image = MemoryCardsSource.cards[randomImages[cardCount]].image
                cardCount++
                if (cardCount == randomImages.size) {
                    cardCount = 0
                }
            }
        }
        return cards
    }

    fun flipCard(value: Boolean, row: Int, column: Int) {
        _uiState.value.cards[row][column].isTurned = value
    }

    fun updateOpenQuestionDialog(value: Boolean) {
        openQuestionDialog = value
    }

    fun onEvent(event: MemoryGameEvent) {
        when(event) {
            MemoryGameEvent.ResetGame -> {
                reset()
            }
        }
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
                MemoryGameViewModel(
                    offlineUserRepository = offlineUserRepository,
                    user = user
                )
            }
        }
    }
}
