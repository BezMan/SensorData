package com.example.facerecognition.presentation

data class FileExportUiState(
    val isGeneratingLoading:Boolean = false,
    val isShareDataClicked:Boolean = false,
    val isSharedDataReady:Boolean = false,
    val shareDataUri:String? = null,
    val failedGenerating:Boolean = false,
    val generatingProgress:Int = 0
)
