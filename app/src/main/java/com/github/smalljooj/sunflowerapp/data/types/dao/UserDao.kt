package com.github.smalljooj.sunflowerapp.data.types.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.github.smalljooj.sunflowerapp.data.types.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)
    @Update
    suspend fun update(user: User)
    @Delete
    suspend fun delete(user: User)
    @Query("DELETE FROM user")
    suspend fun deleteAllUsers()
    @Query("SELECT * FROM user")
    fun getAllUsers(): Flow<List<User>>
    @Query("SELECT * FROM user WHERE id = :id")
    fun getUser(id: Int): Flow<User>
}