package com.example.sensordata.domain.repository

import com.example.sensordata.domain.model.ExportFileInfo
import com.example.sensordata.domain.Resource
import com.example.sensordata.domain.model.ExportModel
import kotlinx.coroutines.flow.Flow

interface IRepository {

    fun startExportData(
        exportList:List<ExportModel>
    ): Flow<Resource<ExportFileInfo>>

}