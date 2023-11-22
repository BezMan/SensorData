package com.example.sensordata.data.repository

import com.example.sensordata.data.csv.IDataConverter
import com.example.sensordata.data.file.IFileWriter
import com.example.sensordata.domain.Resource
import com.example.sensordata.domain.model.ExportFileInfo
import com.example.sensordata.domain.model.ExportModel
import com.example.sensordata.domain.repository.IRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val fileWriter: IFileWriter,
    private val dataConverter: IDataConverter
) : IRepository {

    override fun startExportData(exportList: List<ExportModel>): Flow<Resource<ExportFileInfo>> =
        dataConverter.convertSensorData(exportList, fileWriter)
}