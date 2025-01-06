package com.example.exerciseapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.exerciseapp.view.components.*
import com.example.exerciseapp.viewmodel.ExerciseLogViewModel
import com.example.exerciseapp.viewmodel.ExerciseViewModel
import com.example.exerciseapp.viewmodel.UserViewModel

@Composable
fun ExerciseLogScreen(
    exerciseLogViewModel: ExerciseLogViewModel,
    exerciseViewModel: ExerciseViewModel,
    userViewModel: UserViewModel
) {
    // Observed states
    val allLogs by exerciseLogViewModel.allLogs.observeAsState(listOf())
    val allExercises by exerciseViewModel.allExercises.observeAsState(listOf())
    val user by userViewModel.user.observeAsState(null)
    val allUserRecords by userViewModel.allUserRecords.observeAsState(emptyList())
    val todayLogs by exerciseLogViewModel.todayLogs.observeAsState(listOf())

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
            // Dropdown for activity selection
            ActivityDropdown(
                activities = allExercises,
                selectedActivity = selectedActivity,
                onActivitySelected = { selectedActivity = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Input and Add Button
            AddActivityLog(
                selectedActivity = selectedActivity,
                number = number,
                onClearInput = { number.value = "" },
                exerciseLogViewModel = exerciseLogViewModel
            )

            Spacer(modifier = Modifier.height(16.dp))

            FilterButtons(
                isTodayFilterOn = isTodayFilterOn,
                onToggleToday = { isTodayFilterOn = it },
                onSelectDate = { showDatePicker = true },
                onClearFilters = {
                    isTodayFilterOn = false
                    selectedDate = null
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Log Entries List
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 72.dp)
                ) {
                    items(logsToShow) { log ->
                        ExerciseLogRow(
                            log = log,
                            onDelete = { exerciseLogViewModel.deleteLogById(log.id) }
                        )
                    }
                }
            }
        }

        // Date Picker
        if (showDatePicker) {
            val context = androidx.compose.ui.platform.LocalContext.current
            android.app.DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    val calendar = java.util.Calendar.getInstance()
                    calendar.set(year, month, dayOfMonth)
                    selectedDate = calendar.timeInMillis
                    showDatePicker = false
                },
                java.util.Calendar.getInstance().get(java.util.Calendar.YEAR),
                java.util.Calendar.getInstance().get(java.util.Calendar.MONTH),
                java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH)
            ).show()
        }

        BottomButtons(
            onUserDatabaseClick = { showDialog = true },
            onProgressClick = {
                userViewModel.fetchAllUsers()
                showProgressDialog = true
            },
            modifier = Modifier.align(Alignment.BottomCenter)
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

@Composable
fun FilterButtons(
    isTodayFilterOn: Boolean,
    onToggleToday: (Boolean) -> Unit,
    onSelectDate: () -> Unit,
    onClearFilters: () -> Unit
) {
    Row(
        modifier = Modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = { onToggleToday(!isTodayFilterOn) },
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = if (isTodayFilterOn) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
            )
        ) {
            Text("Today")
        }

        Button(
            onClick = onSelectDate,
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text("Select Date")
        }

        Button(
            onClick = onClearFilters,
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary
            )
        ) {
            Text("Clear Filters")
        }
    }
}

@Composable
fun BottomButtons(
    onUserDatabaseClick: () -> Unit,
    onProgressClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = onUserDatabaseClick,
            modifier = Modifier.weight(1f)
        ) {
            Text("User Database")
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            onClick = onProgressClick,
            modifier = Modifier.weight(1f)
        ) {
            Text("Progress")
        }
    }
}

