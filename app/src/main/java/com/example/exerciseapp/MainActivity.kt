package com.example.exerciseapp

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.exerciseapp.ui.theme.ExerciseAppTheme
import com.example.exerciseapp.view.SplashScreen
import com.example.exerciseapp.viewmodel.ExerciseLogViewModel
import com.example.exerciseapp.viewmodel.ExerciseViewModel
import com.example.exerciseapp.viewmodel.ExerciseLogViewModelFactory
import com.example.exerciseapp.viewmodel.ExerciseViewModelFactory
import android.os.Build
import android.view.WindowInsets
import android.view.WindowManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.example.exerciseapp.data.db.ExerciseLogDatabase
import com.example.exerciseapp.data.repository.UserRepository
import com.example.exerciseapp.navigation.AppNavigation
import com.example.exerciseapp.viewmodel.AppStateViewModel
import com.example.exerciseapp.viewmodel.UserViewModel
import com.example.exerciseapp.viewmodel.UserViewModelFactory
import com.example.exerciseapp.view.components.LoadingScreen
import com.example.exerciseapp.viewmodel.CalorieIntakeViewModel
import com.example.exerciseapp.viewmodel.CalorieIntakeViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemBars()
        setContent {
            val appStateViewModel: AppStateViewModel = viewModel()

            ExerciseAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppContent(appStateViewModel = appStateViewModel, application = application)
                }
            }
        }
    }

    private fun hideSystemBars() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.decorView.setOnApplyWindowInsetsListener { _, insets ->
                window.insetsController?.hide(WindowInsets.Type.statusBars())
                insets
            }
        } else {
            @Suppress("DEPRECATION")
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }
}

@Composable
fun AppContent(appStateViewModel: AppStateViewModel, application: Application) {
    if (appStateViewModel.showSplash) {
        SplashScreen(onTimeout = { appStateViewModel.hideSplashScreen() })
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
        val userViewModel: UserViewModel = viewModel(
            it,
            "UserViewModel",
            UserViewModelFactory(
                UserRepository(
                    ExerciseLogDatabase.getInstance(application).userDao()
                )
            )
        )
        val calorieIntakeViewModel: CalorieIntakeViewModel = viewModel(
            it,
            "CalorieIntakeViewModel",
            CalorieIntakeViewModelFactory(application = application)
        )
        val isLoading by userViewModel.isLoading.observeAsState(false)
        if (isLoading) {
            LoadingScreen()
        } else {
            AppNavigation(
                exerciseLogViewModel = exerciseLogViewModel,
                exerciseViewModel = exerciseViewModel,
                userViewModel = userViewModel,
                calorieIntakeViewModel= calorieIntakeViewModel
            )
        }
    }
}