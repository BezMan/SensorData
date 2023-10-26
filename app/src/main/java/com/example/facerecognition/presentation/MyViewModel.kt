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
import androidx.lifecycle.viewModelScope
import com.example.facerecognition.core.Resource
import com.example.facerecognition.domain.model.ExportModel
import com.example.facerecognition.domain.repository.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val repository: IRepository
) : ViewModel() {

    private val listLimit = 30
//    private val listLimit = 5

    private val exportModelList = mutableListOf<ExportModel>()

    fun onDataCaptured(exportModel: ExportModel) {
        exportModelList.add(exportModel)
    }

    fun getDataList(): List<ExportModel> {
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


    var fileExportState by mutableStateOf(FileExportState())
        private set

    private var collectingJob: Job? = null

    fun generateExportFile() {
        collectingJob?.cancel()
        fileExportState = fileExportState.copy(isGeneratingLoading = true)
        repository.startExportData(
            exportModelList.toList()
        ).onEach { pathInfo ->
            when (pathInfo) {
                is Resource.Success -> {
                    fileExportState = fileExportState.copy(
                        isSharedDataReady = true,
                        isGeneratingLoading = false,
                        shareDataUri = pathInfo.data.path,
                        generatingProgress = 100
                    )
                    onShareDataClick()

                }

                is Resource.Loading -> {

                    delay(1000)

                    pathInfo.data?.let {
                        fileExportState = fileExportState.copy(
                            generatingProgress = pathInfo.data.progressPercentage
                        )
                    }
                }

                is Resource.Error -> {
                    fileExportState = fileExportState.copy(
                        failedGenerating = true,
                        isGeneratingLoading = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }


    fun onShareDataClick() {
        fileExportState = fileExportState.copy(isShareDataClicked = true)
    }

    fun onShareDataOpen() {
        fileExportState = fileExportState.copy(isShareDataClicked = false)
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
