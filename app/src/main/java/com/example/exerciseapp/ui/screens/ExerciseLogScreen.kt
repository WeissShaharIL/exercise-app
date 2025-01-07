package com.example.exerciseapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.exerciseapp.data.entities.CalorieIntake
import com.example.exerciseapp.view.components.*
import com.example.exerciseapp.viewmodel.CalorieIntakeViewModel
import com.example.exerciseapp.viewmodel.ExerciseLogViewModel
import com.example.exerciseapp.viewmodel.ExerciseViewModel
import com.example.exerciseapp.viewmodel.UserViewModel

@Composable
fun ExerciseLogScreen(
    exerciseLogViewModel: ExerciseLogViewModel,
    exerciseViewModel: ExerciseViewModel,
    userViewModel: UserViewModel,
    calorieIntakeViewModel: CalorieIntakeViewModel
) {
    // Observed states
    val allLogs by exerciseLogViewModel.allLogs.observeAsState(listOf())
    val allExercises by exerciseViewModel.allExercises.observeAsState(listOf())
    val calorieIntakes by calorieIntakeViewModel.allCalorieIntake.observeAsState(listOf())
    val user by userViewModel.user.observeAsState(null)
    val allUserRecords by userViewModel.allUserRecords.observeAsState(emptyList())
    val todayLogs by exerciseLogViewModel.todayLogs.observeAsState(listOf())
    val calorieDescription = remember { mutableStateOf("") }
    val calorieAmount = remember { mutableStateOf("") }

    // UI states
    var showProgressDialog by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    val number = remember { mutableStateOf("") }
    var selectedActivity by remember { mutableStateOf("") }
    var isTodayFilterOn by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<Long?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }

    // Logs filtering logic
    val logsToShow = when {
        isTodayFilterOn -> todayLogs
        selectedDate != null -> allLogs.filter { log ->
            val logCalendar = java.util.Calendar.getInstance().apply {
                timeInMillis = log.timestamp
                set(java.util.Calendar.HOUR_OF_DAY, 0)
                set(java.util.Calendar.MINUTE, 0)
                set(java.util.Calendar.SECOND, 0)
                set(java.util.Calendar.MILLISECOND, 0)
            }
            val selectedCalendar = java.util.Calendar.getInstance().apply {
                timeInMillis = selectedDate!!
                set(java.util.Calendar.HOUR_OF_DAY, 0)
                set(java.util.Calendar.MINUTE, 0)
                set(java.util.Calendar.SECOND, 0)
                set(java.util.Calendar.MILLISECOND, 0)
            }
            logCalendar == selectedCalendar
        }

        else -> allLogs
    }

    // UI Layout
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Exercise Input Section
            ActivityDropdown(
                activities = allExercises,
                selectedActivity = selectedActivity,
                onActivitySelected = { selectedActivity = it }
            )
            AddActivityLog(
                selectedActivity = selectedActivity,
                number = number,
                onClearInput = { number.value = "" },
                exerciseLogViewModel = exerciseLogViewModel
            )
            Spacer(modifier = Modifier.height(4.dp))

            FilterButtons(
                isTodayFilterOn = isTodayFilterOn,
                onToggleToday = { isTodayFilterOn = it },
                onSelectDate = { showDatePicker = true },
                onClearFilters = {
                    isTodayFilterOn = false
                    selectedDate = null
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Log Entries List
            Text("Log Entries", style = MaterialTheme.typography.titleMedium)
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 16.dp)
                ) {
                    items(logsToShow) { log ->
                        ExerciseLogRow(
                            log = log,
                            onDelete = { exerciseLogViewModel.deleteLogById(log.id) }
                        )
                        //Text("Track Calorie Intake", style = MaterialTheme.typography.titleMedium)
                    }
                }
            }



            Text("Track Calorie Intake", style = MaterialTheme.typography.titleMedium)
            CalorieIntakeSection(
                calorieDescription = calorieDescription,
                calorieAmount = calorieAmount,
                calorieIntakes = calorieIntakes,
                calorieIntakeViewModel = calorieIntakeViewModel
            )
        }

        DatePicker(
            showDatePicker = showDatePicker,
            onDateSelected = { selectedDate = it },
            onDismissRequest = { showDatePicker = false }
        )

        BottomButtons(
            onUserDatabaseClick = { showDialog = true },
            onProgressClick = {
                userViewModel.fetchAllUsers()
                showProgressDialog = true
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )

        // Other Modals
        if (showDialog) {
            UserDetailsDialog(
                initialHeight = user?.height?.toString() ?: "1.50",
                initialWeight = user?.weight?.toString() ?: "",
                onDismiss = { showDialog = false },
                onSave = { height, weight ->
                    userViewModel.saveUser(height = height, weight = weight)
                    showDialog = false
                }
            )
        }

        if (showProgressDialog) {
            ProgressDialog(
                records = allUserRecords,
                onDismiss = { showProgressDialog = false }
            )
        }
    }
}

