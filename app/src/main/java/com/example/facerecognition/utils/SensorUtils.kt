package com.example.facerecognition.utils

class SensorUtils {


    companion object {

        private const val MIN_LIGHT = 20f
        private const val MAX_LIGHT = 1000f

        fun isLightInRange(lux: Float): Boolean {
            return lux in MIN_LIGHT..MAX_LIGHT
        }
    }

}
