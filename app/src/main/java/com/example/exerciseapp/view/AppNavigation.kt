package com.example.exerciseapp.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.exerciseapp.viewmodel.*

@Composable
fun AppNavigation(
    exerciseLogViewModel: ExerciseLogViewModel,
    exerciseViewModel: ExerciseViewModel,
    userViewModel: UserViewModel
) {
    val navController = rememberNavController()
    val user by userViewModel.user.observeAsState()

    NavHost(
        navController = navController,
        startDestination = if (user == null) "init" else "exerciseLog"
    ) {
        composable("init") {
            InitScreen(
                userViewModel = userViewModel,
                onInitComplete = {
                    navController.navigate("exerciseLog") {
                        popUpTo("init") { inclusive = true }
                    }
                }
            )
        }
        composable("exerciseLog") {
            ExerciseLogScreen(
                exerciseLogViewModel = exerciseLogViewModel,
                exerciseViewModel = exerciseViewModel,
                userViewModel = userViewModel
            )
        }
    }
}
