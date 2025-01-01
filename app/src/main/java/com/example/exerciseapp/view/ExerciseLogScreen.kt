package com.example.exerciseapp.view


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.platform.LocalContext
import com.example.exerciseapp.view.components.ExerciseLogRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.exerciseapp.data.ExerciseLog
import com.example.exerciseapp.view.components.ActivityDropdown
import com.example.exerciseapp.view.components.AddActivityLog
import com.example.exerciseapp.view.components.ProgressDialog
import com.example.exerciseapp.view.components.UserDetailsDialog
import com.example.exerciseapp.viewmodel.ExerciseLogViewModel
import com.example.exerciseapp.viewmodel.ExerciseViewModel
import com.example.exerciseapp.viewmodel.UserViewModel


@Composable
fun ExerciseLogScreen(
    exerciseLogViewModel: ExerciseLogViewModel,
    exerciseViewModel: ExerciseViewModel,
    userViewModel: UserViewModel
) {
    val allLogs by exerciseLogViewModel.allLogs.observeAsState(listOf())
    val allExercises by exerciseViewModel.allExercises.observeAsState(listOf())
    val user by userViewModel.user.observeAsState(null)
    val allUserRecords by userViewModel.allUserRecords.observeAsState(emptyList())

    var showProgressDialog by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    val number = remember { mutableStateOf("") }
    var selectedActivity by remember { mutableStateOf("") }

    LaunchedEffect(allUserRecords) {
        println("DEBUG: LaunchedEffect triggered. allUserRecords size: ${allUserRecords.size}, showProgressDialog: $showProgressDialog")
        if (allUserRecords.isNotEmpty() && !showProgressDialog) {
            showProgressDialog = true
            println("DEBUG: showProgressDialog set to true within LaunchedEffect")
        }
    }


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

            // Scrollable log entries
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
                    items(allLogs) { log ->
                        ExerciseLogRow(
                            log = log,
                            onDelete = { exerciseLogViewModel.deleteLogById(log.id) }
                        )
                    }
                }
            }
        }

        // Buttons at the bottom
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { showDialog = true },
                modifier = Modifier.weight(1f)
            ) {
                Text("User Database")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    println("DEBUG: Progress button clicked")
                    userViewModel.fetchAllUsers()
                    showProgressDialog = false // Ensure previous state doesn't interfere
                    showProgressDialog = true // Trigger dialog opening
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Progress")
            }
        }
    }

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
        Text("DEBUG: Dialog should appear")
        ProgressDialog(
            records = allUserRecords,
            onDismiss = {
                println("DEBUG: ProgressDialog dismissed")
                showProgressDialog = false
            }
        )
    }
}
