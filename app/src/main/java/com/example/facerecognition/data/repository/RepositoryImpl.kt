package com.example.facerecognition.data.repository

import com.example.facerecognition.core.Resource
import com.example.facerecognition.data.converter.IDataConverter
import com.example.facerecognition.data.file.IFileWriter
import com.example.facerecognition.domain.model.ExportModel
import com.example.facerecognition.domain.model.PathInfo
import com.example.facerecognition.domain.repository.IRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val fileWriter: IFileWriter,
    private val dataConverter: IDataConverter
): IRepository {
    override fun startExportData(
        exportList: List<ExportModel>
    ): Flow<Resource<PathInfo>> =
        dataConverter.convertSensorData(exportList).map { generateInfo ->
            when(generateInfo){
                is Resource.Success -> {
                    generateInfo.data.byteArray?.let {
                        when(val result = fileWriter.writeFile(it)){
                            is Resource.Success -> {
                                return@map Resource.Success(
                                    PathInfo(
                                        path = result.data,
                                        progressPercentage = 100
                                    )
                                )
                            }
                            is Resource.Loading -> {
                                return@map Resource.Error(errorMessage = "Unknown Error")
                            }
                            is Resource.Error -> {
                                return@map Resource.Error(errorMessage = result.errorMessage)
                            }
                        }
                    } ?: return@map Resource.Error(errorMessage = "Unkonwn error occured")
                }
                is Resource.Error ->{
                    return@map Resource.Error(errorMessage = generateInfo.errorMessage)
                }
                is Resource.Loading ->{
                    return@map Resource.Loading(
                        PathInfo(
                            progressPercentage = generateInfo.data?.progressPercentage ?: 0
                        )
                    )
                }
            }
        }.flowOn(Dispatchers.IO)
}