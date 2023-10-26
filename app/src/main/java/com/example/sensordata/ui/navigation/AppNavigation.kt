package com.example.sensordata.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sensordata.presentation.MyViewModel
import com.example.sensordata.ui.screens.FaceRecognitionScreen
import com.example.sensordata.ui.screens.LightSensorScreen
import com.example.sensordata.ui.screens.SessionSummaryScreen
import com.example.sensordata.ui.screens.WelcomeScreen

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
            LightSensorScreen(navController)
        }
        composable(Screen.FaceRecognition.route) {
            FaceRecognitionScreen(navController, myViewModel)
        }
        composable(Screen.SessionSummary.route) {
            SessionSummaryScreen(navController, myViewModel)
        }
    }
}

