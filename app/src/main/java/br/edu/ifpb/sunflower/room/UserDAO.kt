package br.edu.ifpb.sunflower.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {
    @Insert
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)
    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM user WHERE id=:id")
    fun getUserById(id: Int): Flow<User>

    @Query("SELECT * from user ORDER BY id ASC LIMIT 1")
    fun getLastUser(): Flow<User>
}