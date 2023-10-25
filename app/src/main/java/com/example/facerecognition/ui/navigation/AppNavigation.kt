package com.example.facerecognition.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.facerecognition.presentation.MyViewModel
import com.example.facerecognition.ui.screens.FaceRecognitionScreen
import com.example.facerecognition.ui.screens.LightSensorScreen
import com.example.facerecognition.ui.screens.SessionSummaryScreen
import com.example.facerecognition.ui.screens.WelcomeScreen

@Composable
fun AppNavigation() {

    val myViewModel: MyViewModel = hiltViewModel()
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(navController, myViewModel)
        }
        composable(Screen.LightSensor.route) {
            LightSensorScreen {
                // Callback for "Try Again" button
                navController.navigate(Screen.Welcome.route) {
                    popUpTo(Screen.Welcome.route) { inclusive = true }
                }
            }
        }
        composable(Screen.FaceRecognition.route) {
            FaceRecognitionScreen(
                navController,
                myViewModel
            )
        }
        composable(Screen.SessionSummary.route) {
            SessionSummaryScreen(
                myViewModel
            ) {
                navController.navigate(Screen.Welcome.route) {
                    popUpTo(Screen.Welcome.route) { inclusive = true }
                }
            }
        }
    }
}

