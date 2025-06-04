package com.dante.macrovia.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun <T> SegmentedOptionSelector(
    title: String,
    options: List<T>,
    selectedOption: T,
    onOptionSelected: (T) -> Unit,
    labelMapper: (T) -> String
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(title, style = MaterialTheme.typography.titleMedium)

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            options.chunked(2).forEach { rowOptions ->
                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    rowOptions.forEachIndexed { index, option ->
                        SegmentedButton(
                            selected = selectedOption == option,
                            onClick = { onOptionSelected(option) },
                            shape = when (rowOptions.size) {
                                1 -> ShapeDefaults.ExtraLarge
                                2 -> when (index) {
                                    0 -> RoundedCornerShape(topStart = 50.dp, bottomStart = 50.dp)
                                    1 -> RoundedCornerShape(topEnd = 50.dp, bottomEnd = 50.dp)
                                    else -> ShapeDefaults.ExtraLarge
                                }
                                else -> ShapeDefaults.ExtraLarge
                            },
                            icon = {
                                if (selectedOption == option) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                            },
                            label = {
                                Text(
                                    labelMapper(option),
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = if (selectedOption == option)
                                            MaterialTheme.colorScheme.onPrimary
                                        else
                                            MaterialTheme.colorScheme.onSurface
                                    ),
                                    textAlign = TextAlign.Center
                                )
                            },
                            colors = SegmentedButtonDefaults.colors(
                                activeContainerColor = MaterialTheme.colorScheme.primary,
                                inactiveContainerColor = MaterialTheme.colorScheme.surface
                            )
                        )
                    }
                }
            }
        }
    }
}