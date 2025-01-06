package com.example.exerciseapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val weight: Float,
    val height: Float,
    @ColumnInfo(name = "timestamp") val timestamp: Long = System.currentTimeMillis()
)
