package com.example.facerecognition.data.repository

import com.example.facerecognition.data.model.SessionData
import javax.inject.Inject

class Repository @Inject constructor(private val fileHandler: IFileHandler) {
    // Your repository methods and logic here

    fun saveDataToFile(data: List<SessionData>) {
        fileHandler.saveDataToFile(data)
    }
}
