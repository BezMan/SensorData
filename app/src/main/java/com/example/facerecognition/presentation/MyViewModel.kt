package com.example.facerecognition.presentation

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.example.facerecognition.SessionData
import com.example.facerecognition.ui.navigation.Screen

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

    private val REQUIRED_PERMISSIONS = arrayOf(
        android.Manifest.permission.CAMERA,
        // Add other necessary permissions here
    )


    var isCameraPermissionGranted by mutableStateOf(false)
    var isCameraStarted by mutableStateOf(false)

    // Add other ViewModel states and dependencies as needed

    fun allPermissionsGranted(): Boolean {
        // Check if all required permissions are granted
        // Implement your logic here
        return isCameraPermissionGranted
    }

    fun requestPermissions(
        activityResultLauncher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>
    ) {
        activityResultLauncher.launch(REQUIRED_PERMISSIONS)
    }

    fun startCamera() {
        // Start the camera
        isCameraStarted = true
    }

}
