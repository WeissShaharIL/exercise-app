package com.example.exerciseapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.exerciseapp.data.entities.CalorieIntake
import com.example.exerciseapp.data.db.ExerciseLogDatabase
import com.example.exerciseapp.data.repository.CalorieIntakeRepository
import kotlinx.coroutines.launch

class CalorieIntakeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: CalorieIntakeRepository
    val allCalorieIntake: LiveData<List<CalorieIntake>>

    init {
        val calorieIntakeDao = ExerciseLogDatabase.getInstance(application).calorieIntakeDao()
        repository = CalorieIntakeRepository(calorieIntakeDao)
        allCalorieIntake = repository.allCalorieIntakes
    }

    // Insert a calorie intake record
    fun insertCalorieIntake(calorieIntake: CalorieIntake) {
        viewModelScope.launch {
            repository.insertCalorieIntake(calorieIntake)
        }
    }

    // Delete a calorie intake record by ID
    fun deleteCalorieIntakeById(id: Int) {
        viewModelScope.launch {
            repository.deleteCalorieIntake(id)
        }
    }
}
