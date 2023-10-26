package com.example.facerecognition.data.csv

import com.example.facerecognition.domain.Resource
import com.example.facerecognition.domain.model.ExportModel
import kotlinx.coroutines.flow.Flow

interface IDataConverter {

    fun convertSensorData(
        exportDataList:List<ExportModel>
    ): Flow<Resource<GenerateInfo>>

}