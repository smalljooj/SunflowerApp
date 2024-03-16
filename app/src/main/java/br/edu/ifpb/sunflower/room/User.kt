package br.edu.ifpb.sunflower.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    val name: String,
    val icon: Int,
    val level: Int,
    val emotion: Int
)