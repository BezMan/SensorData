package com.example.sensordata.data.csv

import com.example.sensordata.domain.Resource
import com.example.sensordata.domain.model.ExportModel
import com.example.sensordata.domain.model.ExportFileInfo
import com.example.sensordata.utils.DateTimeUtils
import com.example.sensordata.utils.SensorUtils
import com.opencsv.CSVWriter
import com.opencsv.CSVWriterBuilder
import com.opencsv.ICSVWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.StringWriter
import java.io.Writer

class DataConverterCSVImpl : IDataConverter {

    private fun getCSVWriter(writer: Writer): ICSVWriter {
        return CSVWriterBuilder(writer)
            .withSeparator(SEPARATOR)
            .withQuoteChar(CSVWriter.NO_QUOTE_CHARACTER)
            .withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
            .withLineEnd(CSVWriter.DEFAULT_LINE_END)
            .build()

    }

    override fun convertSensorData(
        exportDataList: List<ExportModel>
    ): Flow<Resource<ExportFileInfo>> = flow {

        emit(Resource.Loading(ExportFileInfo()))

        val writer = StringWriter()
        val csvWriter = getCSVWriter(writer)
        var alreadyConvertedValues = 0
        csvWriter.writeNext(HEADER_DATA)

        exportDataList.forEach { exportModel ->
            csvWriter.writeNext(
                arrayOf(
                    DateTimeUtils.fileNameFormatToString(exportModel.time),
                    "${SensorUtils.isLightInRange(exportModel.sensorData)}"
                )
            )
            alreadyConvertedValues += 1
            // Calculate progressPercentage based on alreadyConvertedValues and exportDataList.size
            val progressPercentage = (alreadyConvertedValues * 100) / exportDataList.size

            emit(Resource.Loading(ExportFileInfo(progressPercentage = progressPercentage)))
        }
        emit(
            Resource.Success(
                ExportFileInfo(
                    byteArray = String(writer.buffer).toByteArray(),
                )
            )
        )
        csvWriter.close()
        writer.close()
    }
        .flowOn(Dispatchers.IO)

    companion object {
        const val SEPARATOR = ';'
        val HEADER_DATA = arrayOf("timestamp", "is_face_detected")
    }
}