package com.example.exerciseapp.view.components

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
import androidx.compose.ui.Modifier
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.exerciseapp.data.entities.CalorieIntake
import com.example.exerciseapp.viewmodel.CalorieIntakeViewModel

@Composable
fun CalorieIntakeSection(
    calorieDescription: MutableState<String>,
    calorieAmount: MutableState<String>,
    calorieIntakes: List<CalorieIntake>,
    calorieIntakeViewModel: CalorieIntakeViewModel
) {
    Column {
        Text("Track Calorie Intake", style = MaterialTheme.typography.titleMedium)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = calorieDescription.value,
                onValueChange = { calorieDescription.value = it },
                label = { Text("Description") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = calorieAmount.value,
                onValueChange = { calorieAmount.value = it },
                label = { Text("Calories") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = {
                    val calories = calorieAmount.value.toIntOrNull()
                    if (!calorieDescription.value.isBlank() && calories != null && calories > 0) {
                        calorieIntakeViewModel.insertCalorieIntake(
                            CalorieIntake(
                                description = calorieDescription.value,
                                calories = calories
                            )
                        )
                        calorieDescription.value = ""
                        calorieAmount.value = ""
                    }
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Calorie Intake")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
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
                items(calorieIntakes) { intake ->
                    CalorieIntakeRow(
                        intake = intake,
                        onDelete = { calorieIntakeViewModel.deleteCalorieIntakeById(it) }
                    )
                }
            }
        }
    }
}
