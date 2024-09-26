package com.github.smalljooj.sunflowerapp.data.types.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.smalljooj.sunflowerapp.R

@Entity(tableName = "user")
data class User (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val level: Int = 1,
    val image: Int = R.drawable.icon1,
    val imageTitle: Int = R.string.icon1_default,
    val profileId: Int = 0,
    val anxiety: Int = 0,
    val anger: Int = 0,
    val happiness: Int = 0,
    val jealousy: Int = 0,
    val sadness: Int = 0,
    val disgust: Int = 0
)