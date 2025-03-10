package com.example.exerciseapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.exerciseapp.data.entities.ExerciseLog
import com.example.exerciseapp.data.db.ExerciseLogDatabase
import com.example.exerciseapp.data.repository.ExerciseLogRepository
import kotlinx.coroutines.launch

class ExerciseLogViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ExerciseLogRepository
    val allLogs: LiveData<List<ExerciseLog>>
    val todayLogs: LiveData<List<ExerciseLog>>

    init {
        // Initialize database and repository
        val dao = ExerciseLogDatabase.getInstance(application).exerciseLogDao()
        repository = ExerciseLogRepository(dao)
        allLogs = repository.allLogs
        todayLogs = repository.getTodayLogs()
    }

    // Insert a new log
    fun insertLog(log: ExerciseLog) {
        viewModelScope.launch {
            repository.insertLog(log)
        }
    }

    // Delete a log by ID
    fun deleteLogById(id: Int) {
        viewModelScope.launch {
            repository.deleteLogById(id)
        }
    }
}

