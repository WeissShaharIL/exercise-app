package com.example.exerciseapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.exerciseapp.data.entities.Exercise
import com.example.exerciseapp.data.db.ExerciseLogDatabase
import com.example.exerciseapp.data.repository.ExerciseRepository
import kotlinx.coroutines.launch

class ExerciseViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ExerciseRepository
    val allExercises: LiveData<List<Exercise>>

    init {
        val exerciseDao = ExerciseLogDatabase.getInstance(application).exerciseDao()
        repository = ExerciseRepository(exerciseDao) // Initialize repository
        allExercises = repository.allExercises      // Initialize allExercises after repository
    }

    fun insertExercise(name: String) {
        viewModelScope.launch {
            repository.insertExercise(Exercise(name = name))
        }
    }
}
