package com.example.facerecognition.data.repository

import com.example.facerecognition.domain.model.ExportModel
import com.opencsv.CSVReader
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileWriter
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.Charset

class CsvFileHandler : IFileHandler {

    private var writer: FileWriter? = null
    private val creationTime = System.currentTimeMillis()
    private val fileName: String
        get() {
            return "bioeye-$creationTime.csv"
        }


    override fun saveDataToFile(data: List<ExportModel>) {
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

        val file = File(fileName)
        writer = FileWriter(file, true) // Use 'true' for append mode
        writer?.write("timestamp,is_face_detected\n")
    }

    private fun appendDataToFile(data: List<ExportModel>) {
//        val dataToSave = "$timestamp,${if (faceRecognized) 1 else 0}\n"
//        writer?.write(dataToSave)
    }

    private fun closeFile() {
        writer?.close()
    }

    private fun readFile(){
        val inputStream = FileInputStream(fileName)
        val inputStreamReader = InputStreamReader(inputStream, Charset.forName("UTF-8"))
        val reader = BufferedReader(inputStreamReader)

        val csvReader = CSVReader(reader)

        val records = csvReader.readAll()

        for (record in records) {
            val timestamp = record[0]
            val recognized = record[1]

            println("$timestamp, $recognized")
        }
    }

}
