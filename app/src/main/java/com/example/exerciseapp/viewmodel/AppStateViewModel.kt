package com.example.exerciseapp.viewmodel


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AppStateViewModel : ViewModel() {
    var showSplash by mutableStateOf(true)
        private set

    fun hideSplashScreen() {
        showSplash = false
    }
}