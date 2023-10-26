package com.example.sensordata.ui.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object CheckCondition : Screen("checkCondition")
    object CaptureSensorData : Screen("captureSensor")
    object SessionSummary : Screen("sessionSummary")
}
