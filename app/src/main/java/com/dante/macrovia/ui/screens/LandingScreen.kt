package com.dante.macrovia.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dante.macrovia.R
import com.dante.macrovia.ui.components.CustomMaxWidthButton

@Composable
fun LandingScreen(modifier: Modifier = Modifier, onGetStartedClicked: () -> Unit) {
    Column(
        modifier = modifier
            .padding(32.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Macrovia", style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold))
            Spacer(Modifier.height(32.dp))
                Text(
                    "Track your nutrition and achieve your health goals.",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.macrovia_logo),
                contentDescription = "Macrovia Logo",
                modifier = Modifier
                    .size(150.dp)
            )
            Spacer(Modifier.height(12.dp))
            Text("Macrovia", style = MaterialTheme.typography.displayLarge)
        }
        CustomMaxWidthButton(label = "Get Started") {
            onGetStartedClicked()
        }
    }
}