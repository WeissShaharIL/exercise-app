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


    fun getTodayLogs(): LiveData<List<ExerciseLog>> {
        return exerciseLogDao.getTodayLogs()
    }


    // Delete a log by ID
    suspend fun deleteLogById(id: Int) {
        withContext(Dispatchers.IO) {
            exerciseLogDao.deleteLogById(id)
        }
    }
}

class ExerciseRepository(private val exerciseDao: ExerciseDao) {
    val allExercises = exerciseDao.getAllExercises()

    suspend fun insertExercise(exercise: Exercise) {
        exerciseDao.insertExercise(exercise)
    }
}
