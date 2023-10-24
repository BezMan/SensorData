package com.example.facerecognition.presentation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.example.facerecognition.data.model.SessionData
import com.example.facerecognition.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val listLimit = 30
//    private val listLimit = 5

    private val sessionDataList = mutableListOf<SessionData>()

    fun onDataCaptured(sessionData: SessionData) {
        sessionDataList.add(sessionData)
    }

    fun getDataList() : List<SessionData> {
        return sessionDataList
    }


    fun isListAtLimit(): Boolean {
        return sessionDataList.size >= listLimit
    }


    private var inSession by mutableStateOf(false)

    fun startSession() {
        sessionDataList.clear()
        inSession = true
    }

    fun onCameraSessionCompleted() {
        // Call the repository or the CSVFileHandler to save the data to the CSV file
        inSession = false
        repository.saveDataToFile(sessionDataList)
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
            if (ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }


    fun requestPermissions(
        activityResultLauncher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>
    ) {
        activityResultLauncher.launch(REQUIRED_PERMISSIONS)
    }
}
