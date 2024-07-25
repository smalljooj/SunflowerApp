package com.github.smalljooj.sunflowerapp.data.types.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question")
data class Question(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val question: String = "",
    val answers: Boolean = false
)
