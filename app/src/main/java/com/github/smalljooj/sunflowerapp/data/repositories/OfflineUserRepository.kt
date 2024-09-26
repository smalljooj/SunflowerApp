package com.github.smalljooj.sunflowerapp.data.repositories

import com.github.smalljooj.sunflowerapp.data.types.dao.UserDao
import com.github.smalljooj.sunflowerapp.data.types.model.User
import kotlinx.coroutines.flow.Flow

class OfflineUserRepository(
    private val userDao: UserDao
): UserRepository {
    override suspend fun insertUser(user: User) = userDao.insert(user)
    override suspend fun updateUser(user: User) = userDao.update(user)
    override suspend fun deleteUser(user: User) = userDao.delete(user)
    override suspend fun deleteAllUsers() = userDao.deleteAllUsers()
    override fun getAllUsers(): Flow<List<User>> = userDao.getAllUsers()
    override fun getUser(id: Int): Flow<User> = userDao.getUser(id)
}