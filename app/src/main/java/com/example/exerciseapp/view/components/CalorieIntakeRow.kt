package com.example.exerciseapp.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.exerciseapp.data.entities.CalorieIntake

@Composable
fun CalorieIntakeRow(
    intake: CalorieIntake,
    onDelete: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = intake.description, style = MaterialTheme.typography.bodyLarge)
            Text(text = "${intake.calories} Calories", style = MaterialTheme.typography.bodySmall)
        }
        Button(onClick = { onDelete(intake.id) }) {
            Text("Delete")
        }
    }
}
