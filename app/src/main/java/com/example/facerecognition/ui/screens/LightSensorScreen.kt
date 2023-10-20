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

@Composable
fun LightSensorScreen(
    isLightConditionSuitable: Boolean,
    onTryAgain: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (isLightConditionSuitable) {
            // UI for suitable lighting conditions
            Text(text = "Light conditions are suitable")
        } else {
            // UI for unsuitable lighting conditions
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "The room is too bright/dark")
                Button(
                    onClick = { onTryAgain() },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Try Again")
                }
            }
        }
    }
}