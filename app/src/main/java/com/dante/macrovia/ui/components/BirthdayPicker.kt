package com.dante.macrovia.ui.components

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.util.Calendar
import java.util.Locale

@Composable
fun BirthdayPicker(
    titleText: String,
    selectedDate: String,
    onDateSelected: (String) -> Unit
) {
    val context = LocalContext.current

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _: DatePicker, y: Int, m: Int, d: Int ->
                onDateSelected(String.format(Locale.US, "%04d-%02d-%02d", y, m + 1, d))
            },
            year,
            month,
            day
        ).apply {
            datePicker.maxDate = calendar.timeInMillis
        }
    }
    Column (
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        Text(titleText, style = MaterialTheme.typography.titleMedium)

        Text(
            text = selectedDate.ifEmpty { "Birth Date" },
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
                .height(56.dp)
                .background(Color(0xFFF1F2F4), shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .clickable {
                    datePickerDialog.show()
                }
                .padding(16.dp),
            style = MaterialTheme.typography.bodyLarge.copy(
                color = if (selectedDate.isEmpty()) Color(0xFF6A7581) else Color(0xFF121416)
            )
        )
    }
}