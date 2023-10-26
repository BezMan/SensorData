package com.example.facerecognition.domain.repository

import com.example.facerecognition.core.Resource
import com.example.facerecognition.domain.model.ExportModel
import com.example.facerecognition.domain.model.PathInfo
import kotlinx.coroutines.flow.Flow

interface IRepository {

    fun startExportData(
        exportList:List<ExportModel>
    ): Flow<Resource<PathInfo>>

}