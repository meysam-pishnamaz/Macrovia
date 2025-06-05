package com.dante.macrovia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.hilt.navigation.compose.hiltViewModel
import com.dante.macrovia.ui.components.AnimatedNavigationBar
import com.dante.macrovia.ui.components.ButtonData
import com.dante.macrovia.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen() {
    val viewModel: DashboardViewModel = hiltViewModel()
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    val buttons = listOf(
        ButtonData("Home", Icons.Default.Home),
        ButtonData("Food List", Icons.AutoMirrored.Filled.List),
        ButtonData("History", Icons.Default.DateRange),
        ButtonData("Settings", Icons.Default.Settings),
    )

    val swipeModifier = Modifier
        .pointerInput(selectedIndex) {
            detectHorizontalDragGestures { _, dragAmount ->
                if (dragAmount > 75 && selectedIndex > 0) {
                    selectedIndex -= 1
                } else if (dragAmount < -75 && selectedIndex < buttons.lastIndex) {
                    selectedIndex += 1
                }
            }
        }

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = swipeModifier
                .weight(1f)
        ) {
            when (selectedIndex) {
                0 -> HomeScreen()
                1 -> FoodListScreen()
                2 -> HistoryScreen()
                3 -> SettingsScreen()
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom
        ) {
            AnimatedNavigationBar(
                buttons = buttons,
                selectedIndex = selectedIndex,
                onItemSelected = { selectedIndex = it },
                barColor = colorScheme.primary,
                circleColor = colorScheme.primary,
                selectedColor = colorScheme.onPrimary,
                unselectedColor = colorScheme.onPrimary.copy(alpha = 0.35f),
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding())
                    .background(colorScheme.primary)
            )
        }
    }
}