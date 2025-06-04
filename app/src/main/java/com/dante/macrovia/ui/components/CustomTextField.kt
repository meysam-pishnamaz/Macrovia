package com.dante.macrovia.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dante.macrovia.model.InputType

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    titleText: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    inputType: InputType = InputType.TEXT
) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){

        Text(titleText, style = MaterialTheme.typography.titleMedium)
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 12.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            TextField(
                value = value,
                 onValueChange = {
                    val filteredValue = when (inputType) {
                        InputType.NUMBER -> it.filter { char -> char.isDigit() }
                        InputType.DECIMAL -> it.filter { char -> char.isDigit() || char == '.' }
                        InputType.NAME -> it.filter { char ->
                            char.isLetter() || char == ' ' || char == '-' || char == '\''
                        }
                        InputType.TEXT -> it
                    }
                    onValueChange(filteredValue)
                },
                placeholder = {
                    Text(
                        text = placeholderText,
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF1F2F4),
                    unfocusedContainerColor = Color(0xFFF1F2F4),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = Color(0xFF121416),
                    focusedTextColor = Color(0xFF121416),
                    unfocusedTextColor = Color(0xFF121416),
                    focusedPlaceholderColor = Color(0xFF6A7581),
                    unfocusedPlaceholderColor = Color(0xFF6A7581)
                ),
                keyboardOptions = when (inputType) {
                    InputType.NUMBER -> KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
                    InputType.DECIMAL -> KeyboardOptions(keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Next)
                    else -> KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
                }
            )
        }
    }
}