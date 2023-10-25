package com.example.facerecognition.data.file

import com.example.facerecognition.core.Resource

interface FileWriter {

    suspend fun writeFile(byteArray: ByteArray): Resource<String>

    companion object{
        const val FILE_NAME = "bioeye"
    }

}