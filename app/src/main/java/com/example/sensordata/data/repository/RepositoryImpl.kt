package com.example.sensordata.data.repository

import com.example.sensordata.domain.model.ExportFileInfo
import com.example.sensordata.domain.Resource
import com.example.sensordata.data.csv.IDataConverter
import com.example.sensordata.data.file.IFileWriter
import com.example.sensordata.domain.model.ExportModel
import com.example.sensordata.domain.repository.IRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val fileWriter: IFileWriter,
    private val dataConverter: IDataConverter
) : IRepository {
    override fun startExportData(
        exportList: List<ExportModel>
    ): Flow<Resource<ExportFileInfo>> =
        dataConverter.convertSensorData(exportList).map { generateInfo ->
            when (generateInfo) {

                is Resource.Loading -> {
                    return@map Resource.Loading(
                        ExportFileInfo(
                            progressPercentage = generateInfo.data?.progressPercentage ?: 0
                        )
                    )
                }

                is Resource.Error -> {
                    return@map Resource.Error(errorMessage = generateInfo.errorMessage)
                }

                is Resource.Success -> {
                    generateInfo.data.byteArray?.let {
                        when (val result = fileWriter.writeFile(it)) {
                            //no LOADING state for writeFile, only for convertSensorData
                            is Resource.Loading -> {
                                return@map Resource.Error(errorMessage = "Unknown Error")
                            }

                            is Resource.Error -> {
                                return@map Resource.Error(errorMessage = result.errorMessage)
                            }

                            is Resource.Success -> {
                                return@map Resource.Success(
                                    ExportFileInfo(
                                        path = result.data,
                                        progressPercentage = 100
                                    )
                                )
                            }
                        }
                    } ?: return@map Resource.Error(errorMessage = "Unkonwn error occured")
                }

            }
        }.flowOn(Dispatchers.IO)
}