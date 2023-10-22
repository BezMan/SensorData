package com.example.facerecognition.data.repository

import com.example.facerecognition.data.model.FileData
import javax.inject.Inject

class Repository @Inject constructor(private val fileHandler: IFileHandler) {
    // Your repository methods and logic here

    fun saveDataToFile(data: List<FileData>) {
        fileHandler.saveDataToFile(data)
    }
}
