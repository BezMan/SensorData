package com.example.facerecognition.data.repository

import com.example.facerecognition.data.model.FileData
import java.io.File
import java.io.FileWriter
import java.io.IOException

class CsvFileHandler : IFileHandler {

    private var writer: FileWriter? = null

    override fun saveDataToFile(data: List<FileData>) {
        try {
            createFile()
            appendDataToFile(data)
            closeFile()
        } catch (e: IOException) {
            e.printStackTrace()
            // Handle the exception appropriately, e.g., show an error message
        }
    }

    private fun createFile() {
        val creationTime = System.currentTimeMillis()

        val fileName = "bioeye-$creationTime.csv"
        val file = File(fileName)
        writer = FileWriter(file, true) // Use 'true' for append mode
        writer?.write("timestamp,is_face_detected\n")
    }

    private fun appendDataToFile(data: List<FileData>) {
//        val dataToSave = "$timestamp,${if (faceRecognized) 1 else 0}\n"
//        writer?.write(dataToSave)
    }

    private fun closeFile() {
        writer?.close()
    }
}
