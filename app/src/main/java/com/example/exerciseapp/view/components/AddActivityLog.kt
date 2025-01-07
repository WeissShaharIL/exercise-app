package com.example.exerciseapp.view.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.exerciseapp.data.entities.ExerciseLog
import com.example.exerciseapp.viewmodel.ExerciseLogViewModel

@Composable
fun AddActivityLog(
    selectedActivity: String,
    number: MutableState<String>,
    onClearInput: () -> Unit,
    exerciseLogViewModel: ExerciseLogViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically

    )
    {
        OutlinedTextField(
            value = number.value,
            onValueChange = { number.value = it },
            label = { Text("Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .weight(1f) // Allocates equal space to this element
                .padding(end = 8.dp) // Optional spacing for aesthetics
        )

        Button(
            onClick = {
                val count = number.value.toIntOrNull()
                if (selectedActivity.isNotEmpty() && count != null && count > 0) {
                    exerciseLogViewModel.insertLog(
                        ExerciseLog(activity = selectedActivity, number = count)
                    )
                    onClearInput()
                } else {
                    Toast.makeText(
                        context,
                        "Please select an exercise and enter a valid number!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Activity"
            )
        }
    }
}