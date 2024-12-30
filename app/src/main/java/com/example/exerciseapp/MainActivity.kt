package com.example.exerciseapp

import ExerciseLogScreen
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.exerciseapp.ui.theme.ExerciseAppTheme
import com.example.exerciseapp.view.SplashScreen
import com.example.exerciseapp.viewmodel.ExerciseLogViewModel
import com.example.exerciseapp.viewmodel.ExerciseViewModel
import com.example.exerciseapp.viewmodel.ExerciseLogViewModelFactory
import com.example.exerciseapp.viewmodel.ExerciseViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExerciseAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppContent(application = application)

                }
            }
        }
    }
}

@Composable
fun AppContent(application: Application) {
    var showSplash by remember { mutableStateOf(true) }

    if (showSplash) {
        SplashScreen(onTimeout = { showSplash = false })
    } else {
        MainScreen(application = application)
    }
}

@Composable
fun MainScreen(application: Application) {
    val owner = LocalViewModelStoreOwner.current
    owner?.let {
        val exerciseLogViewModel: ExerciseLogViewModel = viewModel(
            it,
            "ExerciseLogViewModel",
            ExerciseLogViewModelFactory(application = application)
        )
        val exerciseViewModel: ExerciseViewModel = viewModel(
            it,
            "ExerciseViewModel",
            ExerciseViewModelFactory(application = application)
        )
        ExerciseLogScreen(
            exerciseLogViewModel = exerciseLogViewModel,
            exerciseViewModel = exerciseViewModel
        )
    }
}