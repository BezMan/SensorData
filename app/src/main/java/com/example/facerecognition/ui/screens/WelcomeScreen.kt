package com.example.facerecognition.ui.screens

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.facerecognition.presentation.MyViewModel
import com.example.facerecognition.ui.navigation.Screen
import com.example.facerecognition.utils.SensorUtils

@Composable
fun WelcomeScreen(navController: NavController, viewModel: MyViewModel) {
    val context = LocalContext.current

    // Request camera permissions
    val activityResultLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val permissionGranted = permissions.entries.all { it.value }

        if (!permissionGranted) {
            Toast.makeText(context, "Permission was denied", Toast.LENGTH_SHORT).show()
        }
    }


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Welcome to Face Recognition App")
            Button(
                onClick = {
                    // Check if all required permissions are granted
                    if (viewModel.areAllPermissionsGranted(context)
                    // Implement your logic here

                    ) {
                        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
                        val lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

// Create a sensor listener to listen for changes in the light sensor value
                        val sensorListener = object : SensorEventListener {
                            override fun onSensorChanged(event: SensorEvent?) {
                                // Get the light sensor Lux value
                                val luxValue = event?.values?.get(0) ?: 0f

                                Log.d("zzzzz welcome", luxValue.toString())

                                // Unregister the sensor listener
                                sensorManager.unregisterListener(this)

                                // Do something with the light sensor value
                                if (SensorUtils.isLightInRange(luxValue)) {

                                    // Navigate to the appropriate screen when light conditions are suitable
                                    navController.navigate(Screen.FaceRecognition.route)
                                } else {
                                    // Navigate to the "LightSensorScreen" when light conditions are not suitable
                                    navController.navigate(Screen.LightSensor.route)
                                }
                            }

                            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                                // Handle changes in the accuracy of the light sensor
                            }
                        }
                        // Register the sensor listener
                        sensorManager.registerListener(sensorListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)

                    } else {
                        viewModel.requestPermissions(activityResultLauncher)
                    }
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Launch Face Detector")
            }
        }
    }
}
