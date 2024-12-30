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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun ExerciseLogScreen(viewModel: ExerciseLogViewModel) {
    val allLogs by viewModel.allLogs.observeAsState(listOf())
    val context = LocalContext.current

    var selectedActivity by remember { mutableStateOf("Push-ups") }
    var number by remember { mutableStateOf("") }
    val activities = listOf("Push-ups", "Squats", "Sit-ups")

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Dropdown for Activity Selection
        Box(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(selectedActivity)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            )  {
                activities.forEach { activity ->
                    DropdownMenuItem(
                        text = { Text(activity) },
                        onClick = {
                            selectedActivity = activity
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Input for Number
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
                if (count != null && count > 0) { // Only proceed if input is valid and greater than 0
                    viewModel.insertLog(ExerciseLog(activity = selectedActivity, number = count))
                    number = "" // Clear the input
                } else {
                    Toast.makeText(context, "Please enter a valid number!", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Exercise")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Logs List
        LazyColumn {
            items(allLogs) { log ->
                ExerciseLogRow(log = log, onDelete = { viewModel.deleteLogById(log.id) })
            }
        }
    }
}

@Composable
fun ExerciseLogRow(log: ExerciseLog, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "${log.activity}: ${log.number}", style = MaterialTheme.typography.bodyLarge)
        IconButton(onClick = { onDelete() }) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Log")
        }
    }
}
