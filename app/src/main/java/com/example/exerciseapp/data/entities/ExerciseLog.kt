package com.example.exerciseapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_log")
data class ExerciseLog(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "logId") val id: Int = 0, // Auto-incremented primary key
    @ColumnInfo(name = "activity") val activity: String, // Activity name
    @ColumnInfo(name = "number") val number: Int, // Count or duration
    @ColumnInfo(name = "timestamp") val timestamp: Long = System.currentTimeMillis() // Time logged
)
