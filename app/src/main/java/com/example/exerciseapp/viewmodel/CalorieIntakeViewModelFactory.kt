package com.example.exerciseapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
class CalorieIntakeViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalorieIntakeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CalorieIntakeViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

