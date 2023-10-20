package com.example.facerecognition.ui.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object LightSensor : Screen("lightSensor")
    object FaceRecognition : Screen("faceRecognition")
    object SessionSummary : Screen("sessionSummary")
}
