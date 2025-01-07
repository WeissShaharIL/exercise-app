package com.example.exerciseapp.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun FilterButtons(
    isTodayFilterOn: Boolean,
    onToggleToday: (Boolean) -> Unit,
    onSelectDate: () -> Unit,
    onClearFilters: () -> Unit
) {
    Row(
        modifier = Modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = { onToggleToday(!isTodayFilterOn) },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isTodayFilterOn) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
            )
        ) {
            Text("Today")
        }

        Button(
            onClick = onSelectDate,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Icon(
                imageVector = Icons.Filled.CalendarToday,
                contentDescription = "Select Date"
            )
        }

        Button(
            onClick = onClearFilters,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary
            )
        ) {
            Text("Clear Filters")
        }
    }
}
