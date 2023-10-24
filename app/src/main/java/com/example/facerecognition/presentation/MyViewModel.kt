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
import com.example.facerecognition.SessionData
import com.example.facerecognition.data.model.FileData
import com.example.facerecognition.data.repository.CsvFileHandler
import com.example.facerecognition.data.repository.IFileHandler

class MyViewModel : ViewModel() {

    private val listLimit = 30
//    private val listLimit = 5

    private val fileDataList = mutableListOf<FileData>()
    private val csvFileHandler: IFileHandler = CsvFileHandler()

    fun onDataCaptured(fileData: FileData) {
        fileDataList.add(fileData)
    }


    fun isListAtLimit(): Boolean {
        return fileDataList.size >= listLimit
    }

    fun onCameraSessionCompleted() {
        // Call the repository or the CSVFileHandler to save the data to the CSV file
        csvFileHandler.saveDataToFile(fileDataList)
    }

    var inSession by mutableStateOf(true)


    var isLightConditionSuitable by mutableStateOf(false)
    var sessionData by mutableStateOf<List<SessionData>>(emptyList())

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
