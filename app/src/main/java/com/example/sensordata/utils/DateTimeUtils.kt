package com.example.sensordata.utils

import java.text.SimpleDateFormat


class DateTimeUtils {

    companion object {

        private const val FILE_NAME_PATTERN = "yyyy-MM-dd_HH:mm:ss"
        private const val TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss"

        fun fileNameFormatToString(time: Long = System.currentTimeMillis()): String {
            val formatter = SimpleDateFormat(FILE_NAME_PATTERN)
            return formatter.format(time)
        }

        fun timestampFormatToString(time: Long = System.currentTimeMillis()): String {
            val formatter = SimpleDateFormat(TIMESTAMP_PATTERN)
            return formatter.format(time)
        }

    }
}