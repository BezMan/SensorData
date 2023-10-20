package com.example.facerecognition.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(navController)
        }
        composable(Screen.LightSensor.route) {
            LightSensorScreen(navController, isLightConditionSuitable = true) {
                // Callback for "Try Again" button
                navController.navigate(Screen.Welcome.route) {
                    popUpTo(Screen.Welcome.route) { inclusive = true }
                }
            }
        }
        composable(Screen.FaceRecognition.route) {
            FaceRecognitionScreen(navController, isFaceRecognized = true) {
                // Callback for "Complete Session" button
                navController.navigate(Screen.SessionSummary.route)
            }
        }
        composable(Screen.SessionSummary.route) {
            SessionSummaryScreen(navController, sessionData = emptyList()) {
                // Callback for "Done" button
                navController.navigate(Screen.Welcome.route) {
                    popUpTo(Screen.Welcome.route) { inclusive = true }
                }
            } {
                // Callback for "Share CSV" button
                // Implement CSV sharing logic here
            }
        }
    }
}
