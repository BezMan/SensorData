package com.example.facerecognition.data.repository

import com.example.facerecognition.data.model.FileData

interface IFileHandler {
    fun saveDataToFile(data: List<FileData>)
}
