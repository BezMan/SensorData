package com.example.facerecognition.domain.repository

import com.example.facerecognition.domain.model.ExportFileInfo
import com.example.facerecognition.domain.Resource
import com.example.facerecognition.domain.model.ExportModel
import kotlinx.coroutines.flow.Flow

interface IRepository {

    fun startExportData(
        exportList:List<ExportModel>
    ): Flow<Resource<ExportFileInfo>>

}