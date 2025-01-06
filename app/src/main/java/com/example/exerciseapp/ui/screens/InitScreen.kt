package com.example.exerciseapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.platform.LocalContext
import com.example.exerciseapp.viewmodel.UserViewModel


@Composable
fun InitScreen(
    userViewModel: UserViewModel,
    onInitComplete: () -> Unit
) {
    var height by remember { mutableStateOf(1.50f) } // Default value for height slider
    var weight by remember { mutableStateOf("") }   // Normal text input for weight
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome! Please enter your details")

        Spacer(modifier = Modifier.height(16.dp))

        // Height input using Slider
        Text("Height: ${String.format("%.2f", height)} meters") // Show 2 decimal places
        Slider(
            value = height,
            onValueChange = { height = it },
            valueRange = 0.50f..2.50f,
            steps = 200, // Allows finer control for 2 decimal points
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Weight input using text field
        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Weight (in kilograms)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val weightValue = weight.toFloatOrNull()
                if (weightValue != null && weightValue > 0) {
                    userViewModel.saveUser(
                        height = String.format("%.2f", height).toDouble().toFloat(), // Save height with 2 decimal places
                        weight = weightValue
                    )
                    Toast.makeText(context, "Details saved successfully!", Toast.LENGTH_SHORT).show()
                    onInitComplete() // Navigate to the main screen
                } else {
                    Toast.makeText(context, "Please enter a valid weight!", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text("Save")
        }
    }
}
