package com.example.exerciseapp.view.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exerciseapp.data.User

@Composable
fun ProgressDialog(
    records: List<User>,
    onDismiss: () -> Unit
) {
    AlertDialog(

        onDismissRequest = { onDismiss() },
        title = { Text("Progress Graph") },
        text = {
            if (records.isEmpty()) {
                Text("No data available.")
            } else {
                ProgressGraph(
                    records = records,
                    lineColor = MaterialTheme.colorScheme.primary,
                    backgroundColor = MaterialTheme.colorScheme.background
                )
            }
        },
        confirmButton = {
            Button(onClick = { onDismiss() }) {
                Text("Close")
            }
        }
    )
}

@Composable
fun ProgressGraph(
    records: List<User>,
    lineColor: androidx.compose.ui.graphics.Color,
    backgroundColor: androidx.compose.ui.graphics.Color
) {
    val sortedRecords = records.sortedBy { it.timestamp } // Sort records by timestamp
    val weights = sortedRecords.map { it.weight }
    val timestamps = sortedRecords.map { it.timestamp }

    // Normalize weights and timestamps to fit into the graph
    val minWeight = weights.minOrNull() ?: 0f
    val maxWeight = weights.maxOrNull() ?: 1f
    val normalizedWeights = if (maxWeight != minWeight) {
        weights.map { (it - minWeight) / (maxWeight - minWeight) }
    } else {
        weights.map { 0.5f }
    }

    val minTimestamp = timestamps.minOrNull() ?: 0L
    val maxTimestamp = timestamps.maxOrNull() ?: 1L
    val normalizedTimestamps = if (maxTimestamp != minTimestamp) {
        timestamps.map { (it - minTimestamp).toFloat() / (maxTimestamp - minTimestamp) }
    } else {
        timestamps.map { 0.5f }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        // Add a Y-axis title
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp),
                //.width(48.dp),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Text(
                text = "Weight",
                modifier = Modifier
                    .weight(1f)
                    .rotate(-45f),
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 9.sp)
            )
            Canvas(modifier = Modifier
                .weight(8f)
                .height(200.dp)) {
                val width = size.width
                val height = size.height

                // Draw background
                drawRect(
                    color = backgroundColor,
                    size = size
                )

                // Draw graph lines
                for (i in 0 until normalizedWeights.size - 1) {
                    val startX = normalizedTimestamps[i] * width
                    val startY = height - (normalizedWeights[i] * height)
                    val endX = normalizedTimestamps[i + 1] * width
                    val endY = height - (normalizedWeights[i + 1] * height)

                    drawLine(
                        color = lineColor,
                        start = Offset(startX, startY),
                        end = Offset(endX, endY),
                        strokeWidth = 4f
                    )
                }

                // Draw data points
                for (i in normalizedWeights.indices) {
                    val x = normalizedTimestamps[i] * width
                    val y = height - (normalizedWeights[i] * height)

                    drawCircle(
                        color = lineColor,
                        center = Offset(x, y),
                        radius = 8f
                    )
                }
            }
        }

        // Add an X-axis title
        Text(
            text = "Time",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodySmall,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}
