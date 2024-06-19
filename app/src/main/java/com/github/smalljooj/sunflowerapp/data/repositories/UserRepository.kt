package com.github.smalljooj.sunflowerapp.data.repositories

import com.github.smalljooj.sunflowerapp.data.types.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun insertUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun deleteUser(user: User)
    suspend fun deleteAllUsers()
    fun getAllUsers(): Flow<List<User>>
    fun getUser(id: Int): Flow<User>
}