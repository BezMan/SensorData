package com.example.sensordata.domain.model

data class ExportFileInfo(
    val path:String? = null,
    val byteArray: ByteArray? = null,
    val progressPercentage:Int = 0
)
