package com.example.facerecognition.data.converter

import com.example.facerecognition.core.Resource
import com.example.facerecognition.domain.model.ExportModel
import kotlinx.coroutines.flow.Flow

interface IDataConverter {

    fun convertSensorData(
        exportDataList:List<ExportModel>
    ): Flow<Resource<GenerateInfo>>

}