package com.example.exerciseapp.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun BottomButtons(
    onUserDatabaseClick: () -> Unit,
    onProgressClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = onUserDatabaseClick,
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFADD8E6).copy(alpha = 0.8f), // Light Blue with transparency
                contentColor = Color.Black // Text fully opaque
            )
        ) {
            Text("Edit Profile")
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            onClick = onProgressClick,
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFADD8E6).copy(alpha = 0.8f), // Light Blue with transparency
                contentColor = Color.Black // Text fully opaque
            )
        ) {
            Text("Progress")
        }
    }
}