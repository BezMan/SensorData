package com.example.facerecognition.presentation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.example.facerecognition.SessionData
import com.example.facerecognition.data.model.FileData
import com.example.facerecognition.data.repository.CsvFileHandler
import com.example.facerecognition.data.repository.IFileHandler
import com.example.facerecognition.ui.navigation.Screen

class MyViewModel : ViewModel() {


    private val fileDataList = mutableListOf<FileData>()
    private val csvFileHandler: IFileHandler = CsvFileHandler()

    fun onCameraPhotoCaptured(timestamp: String, isFaceRecognized: Boolean) {
        val fileData = FileData(timestamp, isFaceRecognized)
        fileDataList.add(fileData)
    }

    fun onCameraSessionCompleted() {
        // Call the repository or the CSVFileHandler to save the data to the CSV file
        csvFileHandler.saveDataToFile(fileDataList)
    }


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
        Manifest.permission.CAMERA,
        // Add other necessary permissions here
    )


    fun areAllPermissionsGranted(context: Context): Boolean {
        for (permission in REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }


    fun requestPermissions(
        activityResultLauncher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>) {
        activityResultLauncher.launch(REQUIRED_PERMISSIONS)
    }
}