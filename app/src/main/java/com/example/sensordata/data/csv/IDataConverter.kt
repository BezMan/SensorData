package com.example.sensordata.data.csv

import com.example.sensordata.domain.Resource
import com.example.sensordata.domain.model.ExportModel
import com.example.sensordata.domain.model.ExportFileInfo
import kotlinx.coroutines.flow.Flow

interface IDataConverter {

    fun convertSensorData(
        exportDataList:List<ExportModel>
    ): Flow<Resource<ExportFileInfo>>

}