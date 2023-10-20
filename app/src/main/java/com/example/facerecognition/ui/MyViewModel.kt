package com.example.facerecognition.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.example.facerecognition.SessionData

class MyViewModel : ViewModel() {

    var currentScreen by mutableStateOf<Screen>(Screen.Welcome)
    var isLightConditionSuitable by mutableStateOf(false)
    var isCameraActive by mutableStateOf(false)
    var cameraPreview by mutableStateOf<Preview?>(null)
    var isFaceRecognized by mutableStateOf(false)
    var sessionData by mutableStateOf<List<SessionData>>(emptyList())
    var sessionDuration by mutableStateOf(0L)
    var isSessionSummaryScreenActive by mutableStateOf(false)
    var generatedCsv by mutableStateOf<String?>(null)

    // Functions to update state and perform actions based on user interactions.

    fun startSession() {
        // Start a new session.
    }

    fun completeSession() {
        // Complete the session and navigate to the summary screen.
    }

    fun shareCsv() {
        // Generate and share the CSV file.
    }
}
