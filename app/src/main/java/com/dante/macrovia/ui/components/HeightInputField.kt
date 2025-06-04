package com.dante.macrovia.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dante.macrovia.model.InputType
import com.dante.macrovia.model.Units

@Composable
fun HeightInputField(
    unit: Units,
    heightInFeet: String,
    heightInInches: String,
    heightInCm: String,
    onFeetChanged: (String) -> Unit,
    onInchesChanged: (String) -> Unit,
    onCmChanged: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("What’s your height?", style = MaterialTheme.typography.titleMedium)

        when (unit) {
            Units.Imperial -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CustomTextField(
                        titleText = "Height (feet)",
                        value = heightInFeet,
                        onValueChange = onFeetChanged,
                        placeholderText = "e.g. 5",
                        modifier = Modifier.weight(1f),
                        inputType =  InputType.NUMBER
                    )
                    CustomTextField(
                        titleText = "Height (inches)",
                        value = heightInInches,
                        onValueChange = onInchesChanged,
                        placeholderText = "e.g. 8",
                        modifier = Modifier.weight(1f),
                        inputType =  InputType.NUMBER
                    )
                }
            }
            Units.Metric -> {
                CustomTextField(
                    titleText ="Height (cm)",
                    value = heightInCm,
                    onValueChange = onCmChanged,
                    placeholderText = "e.g. 172",
                    inputType =  InputType.NUMBER
                )
            }
        }
    }
}