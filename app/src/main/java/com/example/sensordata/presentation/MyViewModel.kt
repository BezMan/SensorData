package com.example.sensordata.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sensordata.domain.Resource
import com.example.sensordata.domain.model.ExportModel
import com.example.sensordata.domain.repository.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val repository: IRepository
) : ViewModel() {

    private val listLimit = 30
//    private val listLimit = 5

    private val exportModelList = mutableListOf<ExportModel>()

    var inSession by mutableStateOf(false)
        private set

    var fileExportUiState by mutableStateOf(FileExportUiState())
        private set

    private var collectingJob: Job? = null


    fun onDataCaptured(exportModel: ExportModel) {
        exportModelList.add(exportModel)
    }

    fun getDataList(): List<ExportModel> {
        return exportModelList
    }


    fun isListAtLimit(): Boolean {
        return exportModelList.size >= listLimit
    }

    fun startSession() {
        exportModelList.clear()
        inSession = true
    }

    fun onCameraSessionCompleted() {
        // Call the repository or the CSVFileHandler to save the data to the CSV file
        inSession = false
        repository.startExportData(exportModelList)
    }


    fun onShareDataClick() {
        fileExportUiState = fileExportUiState.copy(isShareDataClicked = true)
    }

    fun onShareDataOpen() {
        fileExportUiState = fileExportUiState.copy(isShareDataClicked = false)
    }

    fun generateExportFile() {
        // Cancel the previous job if it exists
        collectingJob?.cancel()

        collectingJob = viewModelScope.launch {

            fileExportUiState = fileExportUiState.copy(
                isGeneratingLoading = true
            )

            repository.startExportData(
                exportModelList.toList()
            ).onEach { pathInfo ->
                when (pathInfo) {
                    is Resource.Success -> {
                        fileExportUiState = fileExportUiState.copy(
                            isSharedDataReady = true,
                            isGeneratingLoading = false,
                            shareDataUri = pathInfo.data.path,
                            generatingProgress = 100
                        )
                        onShareDataClick()

                    }

                    is Resource.Loading -> {

                        delay(300)

                        pathInfo.data?.let {
                            fileExportUiState = fileExportUiState.copy(
                                generatingProgress = pathInfo.data.progressPercentage
                            )
                        }
                    }

                    is Resource.Error -> {
                        fileExportUiState = fileExportUiState.copy(
                            isGeneratingLoading = false
                        )
                    }
                }
            }
        }
    }


}
