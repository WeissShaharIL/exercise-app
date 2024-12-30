package com.example.exerciseapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ExerciseLogViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExerciseLogViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExerciseLogViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
