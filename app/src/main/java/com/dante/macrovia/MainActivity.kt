package com.dante.macrovia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dante.macrovia.ui.screens.DashboardScreen
import com.dante.macrovia.ui.screens.LandingScreen
import com.dante.macrovia.ui.screens.UserProfileSetupScreen
import com.dante.macrovia.ui.theme.MacroviaTheme
import com.dante.macrovia.utils.Screen
import com.dante.macrovia.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MacroviaTheme {
                NavigationHost()
            }
        }
    }
}

@Composable
fun NavigationHost() {
    val viewModel: MainViewModel = hiltViewModel()

    val userExists = viewModel.userExists()

    val navController = rememberNavController()

    Scaffold { innerPadding ->
        NavHost(navController = navController, startDestination = if (userExists) Screen.DASHBOARD else Screen.LANDING_SCREEN,
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
        ) {
            composable(Screen.LANDING_SCREEN) { LandingScreen(
                onGetStartedClicked = {
                    navController.navigate(Screen.USER_PROFILE_SETUP)
                }
            ) }
            composable(Screen.USER_PROFILE_SETUP) {
                UserProfileSetupScreen(
                    onBackClicked = {
                        navController.popBackStack()
                    },
                    onSuccessfulSubmit = {
                        navController.navigate(Screen.DASHBOARD) {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable(Screen.DASHBOARD) {
                DashboardScreen()
            }
        }
    }
}