package com.example.facerecognition.data.repository

import com.example.facerecognition.data.model.SessionData

interface IFileHandler {
    fun saveDataToFile(data: List<SessionData>)
}
