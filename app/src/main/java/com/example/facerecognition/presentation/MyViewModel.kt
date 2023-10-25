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
import com.example.facerecognition.domain.model.ExportModel
import com.example.facerecognition.domain.repository.ExportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val repository: ExportRepository
) : ViewModel() {

    private val listLimit = 30
//    private val listLimit = 5

    private val exportModelList = mutableListOf<ExportModel>()

    fun onDataCaptured(exportModel: ExportModel) {
        exportModelList.add(exportModel)
    }

    fun getDataList() : List<ExportModel> {
        return exportModelList
    }


    fun isListAtLimit(): Boolean {
        return exportModelList.size >= listLimit
    }


    var inSession by mutableStateOf(false)

    fun startSession() {
        exportModelList.clear()
        inSession = true
    }

    fun onCameraSessionCompleted() {
        // Call the repository or the CSVFileHandler to save the data to the CSV file
        inSession = false
        repository.startExportData(exportModelList)
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
