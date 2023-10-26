package com.example.facerecognition.data.file

import com.example.facerecognition.domain.Resource

interface IFileWriter {

    suspend fun writeFile(byteArray: ByteArray): Resource<String>

    companion object{
        const val FILE_NAME = "bioeye"
    }

}