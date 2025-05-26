package com.dante.Macrovia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dante.Macrovia.ui.theme.MacroviaTheme
import com.dante.Macrovia.utilities.Screen

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
    val navController = rememberNavController()
    Scaffold { innerPadding ->
        NavHost(navController = navController, startDestination = Screen.OnBoardingScreen,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.OnBoardingScreen) { OnboardingScreen(
                onGetStartedClicked = {
                    navController.navigate(Screen.AboutYou)
                }
            ) }
            composable(Screen.AboutYou) { AboutYouScreen(onBackClicked = {
                    navController.popBackStack()
            })}
        }
    }
}