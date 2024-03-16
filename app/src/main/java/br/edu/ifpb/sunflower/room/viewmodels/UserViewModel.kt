package br.edu.ifpb.sunflower.room.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifpb.sunflower.room.User
import br.edu.ifpb.sunflower.room.UserDAO
import br.edu.ifpb.sunflower.room.states.UserState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UserViewModel(
    private val dao: UserDAO
): ViewModel() {
    var state by mutableStateOf(UserState())
    private set
    init {
        viewModelScope.launch {
            dao.getUserById(0).collectLatest {
                state = state.copy(
                    user = it
                )
            }
        }
    }

    fun getLastUser(): Flow<User> {
        return dao.getLastUser()
    }

    fun insertUser(user: User) = viewModelScope.launch {
        dao.insert(user)
    }

    fun updateUser(user: User) = viewModelScope.launch {
        dao.update(user)
    }

    fun deleteUser(user: User) = viewModelScope.launch {
        dao.delete(user)
    }
}