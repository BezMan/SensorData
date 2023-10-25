package com.example.facerecognition.data.repository

import com.example.facerecognition.domain.model.ExportModel
import javax.inject.Inject

class Repository @Inject constructor(private val fileHandler: IFileHandler) {
    // Your repository methods and logic here

    fun saveDataToFile(data: List<ExportModel>) {
        fileHandler.saveDataToFile(data)
    }
}
