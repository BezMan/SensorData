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
            LightSensorScreen(isLightConditionSuitable = true) {
                // Callback for "Try Again" button
                navController.navigate(Screen.Welcome.route) {
                    popUpTo(Screen.Welcome.route) { inclusive = true }
                }
            }
        }
        composable(Screen.FaceRecognition.route) {
            FaceRecognitionScreen(
                navController,
                imageCapture = myViewModel.imageCapture, // Injected ImageCapture via Hilt
                faceDetector = myViewModel.faceDetector, // Injected FaceDetector via Hilt
                myViewModel
            )
        }
        composable(Screen.SessionSummary.route) {
            SessionSummaryScreen(
                sessionData = myViewModel.sessionData,
                onDoneClicked = {
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                },
                onShareCsvClicked = {
                    // Implement CSV sharing logic here
                }
            )
        }
    }
}

