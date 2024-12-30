package com.example.exerciseapp.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.exerciseapp.data.Exercise

@Composable
fun ActivityDropdown(
    activities: List<Exercise>,
    selectedActivity: String,
    onActivitySelected: (String) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { expanded.value = true }) {
            Text(selectedActivity.ifEmpty { "Select Exercise" })
        }
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            activities.forEach { exercise ->
                DropdownMenuItem(
                    text = { Text(exercise.name) },
                    onClick = {
                        onActivitySelected(exercise.name)
                        expanded.value = false
                    }
                )
            }
        }
    }
}
