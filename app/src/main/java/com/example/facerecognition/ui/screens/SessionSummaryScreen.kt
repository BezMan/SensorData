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
import com.example.facerecognition.SessionData

@Composable
fun SessionSummaryScreen(
    sessionData: List<SessionData>,
    onDoneClicked: () -> Unit,
    onShareCsvClicked: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Session Summary")

            // Display session duration, face detected duration, and face not detected duration
            Text(text = "Session Duration: ${calculateSessionDuration(sessionData)}")
            Text(text = "Face Detected Duration: ${calculateFaceDetectedDuration(sessionData)}")
            Text(text = "Face Not Detected Duration: ${calculateFaceNotDetectedDuration(sessionData)}")

            Button(
                onClick = { onDoneClicked() },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Done")
            }

            Button(
                onClick = { onShareCsvClicked() },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Share CSV")
            }
        }
    }
}

// Implement these functions to calculate durations based on your session data
fun calculateSessionDuration(sessionData: List<SessionData>): String {
    // Implement the logic to calculate session duration
    return "HH:mm:ss"
}

fun calculateFaceDetectedDuration(sessionData: List<SessionData>): String {
    // Implement the logic to calculate face detected duration
    return "HH:mm:ss"
}

fun calculateFaceNotDetectedDuration(sessionData: List<SessionData>): String {
    // Implement the logic to calculate face not detected duration
    return "HH:mm:ss"
}
