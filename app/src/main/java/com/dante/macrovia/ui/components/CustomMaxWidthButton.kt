package com.dante.macrovia.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CustomMaxWidthButton(
    label: String,
    onclick: ()-> Unit
){
    Button(
        onClick = onclick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Text(label, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
    }
}