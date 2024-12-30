package com.example.exerciseapp.view

import android.widget.Toast
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.exerciseapp.data.ExerciseLog
import com.example.exerciseapp.view.components.ActivityDropdown
import com.example.exerciseapp.view.components.AddActivityLog
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

    var selectedActivity by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    val number = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ActivityDropdown(
            activities = allExercises,
            selectedActivity = selectedActivity,
            onActivitySelected = { selectedActivity = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        AddActivityLog(
            selectedActivity = selectedActivity,
            number = number,
            onClearInput = { number.value = "" },
            exerciseLogViewModel = exerciseLogViewModel
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(allLogs) { log ->
                ExerciseLogRow(log = log, onDelete = { exerciseLogViewModel.deleteLogById(log.id) })
            }
        }

        val buttonColor = if (user == null) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f) else MaterialTheme.colorScheme.primary
        Button(
            onClick = { showDialog = true },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            enabled = true,
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = buttonColor)
        ) {
            Text("User Database Button")
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
}