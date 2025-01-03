package com.example.exerciseapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExerciseLogDao {
    @Insert
    suspend fun insertLog(log: ExerciseLog) // Insert a new log

    @Query("SELECT * FROM exercise_log ORDER BY timestamp DESC")
    fun getAllLogs(): LiveData<List<ExerciseLog>> // Retrieve all logs, sorted by timestamp

    @Query("SELECT * FROM exercise_log WHERE date(timestamp / 1000, 'unixepoch') = date('now') ORDER BY timestamp DESC")
    fun getTodayLogs(): LiveData<List<ExerciseLog>>

    @Query("DELETE FROM exercise_log WHERE logId = :id")
    suspend fun deleteLogById(id: Int) // Delete a log by its ID
}
