package com.example.exerciseapp.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExerciseLogRepository(private val exerciseLogDao: ExerciseLogDao) {

    // Retrieve all exercise logs
    val allLogs: LiveData<List<ExerciseLog>> = exerciseLogDao.getAllLogs()

    // Insert a new log
    suspend fun insertLog(log: ExerciseLog) {
        withContext(Dispatchers.IO) {
            exerciseLogDao.insertLog(log)
        }
    }

    // Delete a log by ID
    suspend fun deleteLogById(id: Int) {
        withContext(Dispatchers.IO) {
            exerciseLogDao.deleteLogById(id)
        }
    }
}
