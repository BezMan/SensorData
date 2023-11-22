package com.example.sensordata.data.file

import com.example.sensordata.domain.Resource

interface IFileWriter {

    suspend fun writeFile(byteArray: ByteArray): Resource<String>

    companion object{
        const val FILE_NAME = "sendata"
    }

}