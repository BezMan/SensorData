package com.example.facerecognition.data.repository

import com.example.facerecognition.domain.model.ExportModel

interface IFileHandler {
    fun saveDataToFile(data: List<ExportModel>)
}
