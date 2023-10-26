package com.example.sensordata.presentation

data class FileExportState(
    val isGeneratingLoading:Boolean = false,
    val isShareDataClicked:Boolean = false,
    val shareDataUri:String? = null,
    val generatingProgress:Int = 0
)
