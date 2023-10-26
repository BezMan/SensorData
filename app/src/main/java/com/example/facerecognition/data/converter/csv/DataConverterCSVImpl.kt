package com.example.facerecognition.data.converter.csv

import com.example.facerecognition.core.Resource
import com.example.facerecognition.domain.model.ExportModel
import com.example.facerecognition.data.converter.IDataConverter
import com.example.facerecognition.data.converter.GenerateInfo
import com.example.facerecognition.utils.DateTimeUtils
import com.example.facerecognition.utils.SensorUtils
import com.opencsv.CSVWriter
import com.opencsv.CSVWriterBuilder
import com.opencsv.ICSVWriter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.StringWriter
import java.io.Writer

class DataConverterCSVImpl : IDataConverter {

    private fun getCSVWriter(writer: Writer):ICSVWriter{
        return CSVWriterBuilder(writer)
            .withSeparator(SEPARATOR)
            .withQuoteChar(CSVWriter.NO_QUOTE_CHARACTER)
            .withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
            .withLineEnd(CSVWriter.DEFAULT_LINE_END)
            .build()

    }

    override fun convertSensorData(
        exportDataList: List<ExportModel>
    ): Flow<Resource<GenerateInfo>> = flow {
        emit(Resource.Loading(GenerateInfo()))
        val writer = StringWriter()
        val csvWriter = getCSVWriter(writer)
        val valuesForOnePercent = (exportDataList.size / 100)+1
        var alreadyConvertedValues = 0
        csvWriter.writeNext(HEADER_DATA)

        exportDataList.forEach { exportModel ->
            csvWriter.writeNext(
                arrayOf(
                    DateTimeUtils.timestampFormatToString(exportModel.time),
                    "${SensorUtils.isLightInRange(exportModel.sensorData)}"
                )
            )
            alreadyConvertedValues += 1
            if(alreadyConvertedValues%valuesForOnePercent == 0){
                emit(
                    Resource.Loading(
                        GenerateInfo(
                    progressPercentage = alreadyConvertedValues/valuesForOnePercent
                )
                    ))
            }
        }
        emit(
            Resource.Success(
            GenerateInfo(
                byteArray = String(writer.buffer).toByteArray(),
                progressPercentage = 100
            )
        ))
        csvWriter.close()
        writer.close()
    }

    companion object{
        const val SEPARATOR = ';'
        val HEADER_DATA = arrayOf("timestamp","is_face_detected")
    }
}