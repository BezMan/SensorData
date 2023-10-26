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
import kotlinx.coroutines.flow.launchIn
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

    var fileExportState by mutableStateOf(FileExportState())
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
        fileExportState = fileExportState.copy(isShareDataClicked = true)
    }

    fun onShareDataOpen() {
        fileExportState = fileExportState.copy(isShareDataClicked = false)
    }

    fun generateExportFile() {
        // Cancel the previous job if it exists
        collectingJob?.cancel()

        collectingJob = viewModelScope.launch {

            fileExportState = fileExportState.copy(
                isGeneratingLoading = true
            )

            repository.startExportData(
                exportModelList.toList()
            ).onEach { fileExportState ->
                when (fileExportState) {
                    is Resource.Success -> {
                        this@MyViewModel.fileExportState = this@MyViewModel.fileExportState.copy(
                            isGeneratingLoading = false,
                            shareDataUri = fileExportState.data.path,
                            generatingProgress = 100
                        )
                        onShareDataClick()

                    }

                    is Resource.Loading -> {

                        //delay for viewing the LOADING
                        delay(300)

                        fileExportState.data?.let {
                            this@MyViewModel.fileExportState = this@MyViewModel.fileExportState.copy(
                                generatingProgress = fileExportState.data.progressPercentage
                            )
                        }
                    }

                    is Resource.Error -> {
                        this@MyViewModel.fileExportState = this@MyViewModel.fileExportState.copy(
                            isGeneratingLoading = false
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }


}
