package com.github.smalljooj.sunflowerapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.smalljooj.sunflowerapp.data.types.dao.UserDao
import com.github.smalljooj.sunflowerapp.data.types.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class SunflowerDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object{
        private var Instance: SunflowerDatabase? = null
        fun getDatabase(context: Context): SunflowerDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context = context,
                    klass = SunflowerDatabase::class.java,
                    name = "sunflower_db"
                ).fallbackToDestructiveMigrationFrom()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}