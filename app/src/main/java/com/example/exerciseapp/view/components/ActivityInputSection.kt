package com.example.exerciseapp.view.components


import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import com.example.exerciseapp.viewmodel.ExerciseLogViewModel
import com.example.exerciseapp.data.entities.Exercise

@Composable
fun ActivityInputSection(
    activities: List<Exercise>, // List of Exercise objects
    selectedActivity: String,
    onActivitySelected: (String) -> Unit,
    number: MutableState<String>,
    onClearInput: () -> Unit,
    exerciseLogViewModel: ExerciseLogViewModel
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        ActivityDropdown(
            activities = activities,
            selectedActivity = selectedActivity,
            onActivitySelected = onActivitySelected
        )
        AddActivityLog(
            selectedActivity = selectedActivity,
            number = number,
            onClearInput = onClearInput,
            exerciseLogViewModel = exerciseLogViewModel
        )
    }
}
