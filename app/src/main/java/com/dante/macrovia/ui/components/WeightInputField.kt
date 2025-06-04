package com.dante.macrovia.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.dante.macrovia.model.InputType
import com.dante.macrovia.model.Units

@Composable
fun WeightInputField(
    unit: Units,
    weightInPound: String,
    weightInKg: String,
    onPoundChanged: (String) -> Unit,
    onKilogramChanged: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("What’s your weight?", style = MaterialTheme.typography.titleMedium)

        when (unit) {
            Units.Imperial -> {
                CustomTextField(
                    titleText = "Weight (lbs)",
                    value = weightInPound,
                    onValueChange = onPoundChanged,
                    placeholderText = "e.g. 180",
                    inputType =  InputType.NUMBER
                )
            }
            Units.Metric -> {
                CustomTextField(
                    titleText = "Weight (kg)",
                    value = weightInKg,
                    onValueChange = onKilogramChanged,
                    placeholderText = "e.g. 82",
                    inputType =  InputType.NUMBER
                )
            }
        }
    }
}