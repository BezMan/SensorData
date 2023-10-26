package com.example.sensordata.ui.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object LightSensor : Screen("lightSensor")
    object FaceRecognition : Screen("faceRecognition")
    object SessionSummary : Screen("sessionSummary")
}
