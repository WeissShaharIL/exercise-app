package com.example.exerciseapp.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.platform.LocalContext
import com.example.exerciseapp.view.components.ExerciseLogRow
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.exerciseapp.data.ExerciseLog
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
    val context = LocalContext.current
    val user by userViewModel.user.observeAsState(null)
    var selectedActivity by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    var height by remember { mutableStateOf("1.50") } // Default height as a string
    var weight by remember { mutableStateOf("") }

// Pre-fill height and weight when the dialog is opened
    LaunchedEffect(showDialog) {
        if (showDialog) {
            height = user?.height?.toString() ?: ""
            weight = user?.weight?.toString() ?: ""
        }
    }


    // Update the selected activity dynamically when allExercises changes
    LaunchedEffect(allExercises) {
        if (allExercises.isNotEmpty() && selectedActivity.isEmpty()) {
            selectedActivity = allExercises[0].name // Set the first exercise as default
        }
    }
    var number by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Dropdown for Activity Selection
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = { expanded = true },
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Text(selectedActivity)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                allExercises.forEach { exercise ->
                    DropdownMenuItem(
                        text = { Text(exercise.name) },
                        onClick = {
                            selectedActivity = exercise.name
                            expanded = false
                        }
                    )
                }
            }
        }


        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = number,
            onValueChange = { number = it },
            label = { Text("Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val count = number.toIntOrNull()
                if (selectedActivity != "Select Exercise" && count != null && count > 0) {
                    exerciseLogViewModel.insertLog(
                        ExerciseLog(activity = selectedActivity, number = count)
                    )
                    number = "" // Clear input
                } else {
                    Toast.makeText(
                        context,
                        "Please select an exercise and enter a valid number!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier.padding(horizontal = 8.dp),
            ) {
            Text("Add")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Logs List
        LazyColumn {
            items(allLogs) { log ->
                ExerciseLogRow(log = log, onDelete = { exerciseLogViewModel.deleteLogById(log.id) })
            }
        }
        // Button at the Bottom
        val buttonColor = if (user == null) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f) else MaterialTheme.colorScheme.primary
        Button(
            onClick = { showDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            enabled = true,
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = buttonColor)
        ) {
            Text("User Database Button")
        }
    }
    if (showDialog) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        val heightValue = height.toFloatOrNull()
                        val weightValue = weight.toFloatOrNull()
                        if (heightValue != null && heightValue > 0 && weightValue != null && weightValue > 0) {
                            userViewModel.saveUser(height = heightValue, weight = weightValue)
                            Toast.makeText(context, "User details saved successfully!", Toast.LENGTH_SHORT).show()
                            showDialog = false // Close the dialog
                        } else {
                            Toast.makeText(context, "Please enter a valid height and weight!", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            },
            title = { Text("Enter User Details") },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Height: ${String.format("%.2f", height.toFloatOrNull() ?: 1.5f)} m",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Slider(
                        value = height.toFloatOrNull() ?: 1.5f, // Safe conversion with default
                        onValueChange = { newHeight -> height = String.format("%.2f", newHeight) }, // Format to 2 decimals
                        valueRange = 0.5f..2.5f,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = weight,
                        onValueChange = { weight = it },
                        label = { Text("Weight (e.g., 70 kg)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        )
    }

}
