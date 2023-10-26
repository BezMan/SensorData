package com.example.facerecognition.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.facerecognition.ui.navigation.Screen

@Composable
fun LightSensorScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // UI for unsuitable lighting conditions
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "The room is too bright/dark")
            Button(
                onClick = {
                    onTryAgainClicked(navController)
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Try Again")
            }
        }
    }

}

private fun onTryAgainClicked(navController: NavController) {
    navController.navigate(Screen.Welcome.route) {
        popUpTo(Screen.Welcome.route) { inclusive = true }
    }
}