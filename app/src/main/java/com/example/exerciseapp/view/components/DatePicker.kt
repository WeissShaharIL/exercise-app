package com.example.exerciseapp.view.components

import androidx.compose.runtime.Composable


@Composable
fun DatePicker(
    showDatePicker: Boolean,
    onDateSelected: (Long) -> Unit,
    onDismissRequest: () -> Unit
) {
    if (showDatePicker) {
        val context = androidx.compose.ui.platform.LocalContext.current
        android.app.DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val calendar = java.util.Calendar.getInstance()
                calendar.set(year, month, dayOfMonth)
                onDateSelected(calendar.timeInMillis)
                onDismissRequest() // Ensure state is reset
            },
            java.util.Calendar.getInstance().get(java.util.Calendar.YEAR),
            java.util.Calendar.getInstance().get(java.util.Calendar.MONTH),
            java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH)
        ).apply {
            // Reset state when the dialog is canceled or dismissed
            setOnCancelListener { onDismissRequest() }
            setOnDismissListener { onDismissRequest() }
        }.show()
    }
}
