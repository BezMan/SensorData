package com.example.facerecognition.ui.screens

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.camera.core.CameraSelector
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.facerecognition.data.model.FileData
import com.example.facerecognition.utils.MyUtils
import com.example.facerecognition.presentation.MyViewModel
import com.example.facerecognition.ui.navigation.Screen

@Composable
fun FaceRecognitionScreen(
    navController: NavController,
    viewModel: MyViewModel
) {
    val context = LocalContext.current

    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_CAPTURE or CameraController.VIDEO_CAPTURE)
        }
    }
    controller.cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

    // State to track the number of photos taken
    var taken by remember { mutableIntStateOf(0) }
    // Lux value
    var lux by remember { mutableIntStateOf(0) }

    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

    val sensorListener = object : SensorEventListener {
        private var lastEventTime = 0L

        override fun onSensorChanged(event: SensorEvent?) {
            if (viewModel.isListAtLimit()) {
                endSession(navController, viewModel)
            } else {
                // Get the current time
                val currentTime = System.currentTimeMillis()

                // Check if the last event was more than 1000 ms ago
                if (currentTime - lastEventTime >= 1000L) {
                    // Get the light sensor Lux value
                    val luxValue = event?.values?.get(0) ?: 0f
                    lux = luxValue.toInt() // Update the lux value

                    lastEventTime = currentTime
                    // Do something with the light sensor Lux value

                    val fileData = FileData(currentTime.toString(), MyUtils.isLightInRange(luxValue))
                    viewModel.onDataCaptured(fileData)
                }
            }
        }

        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}
    }

    DisposableEffect(lightSensor) {
        sensorManager.registerListener(sensorListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
        onDispose {
            sensorManager.unregisterListener(sensorListener)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Display the camera preview
        CameraPreview(
            modifier = Modifier.fillMaxSize(),
            controller = controller
        )
        // Lux value
        Text(
            text = "Lux: $lux", // Display the lux value
            modifier = Modifier.align(Alignment.Center)
        )

        Button(
            onClick = {
                endSession(navController, viewModel)
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Text("Complete Session")
        }
    }
}

private fun endSession(
    navController: NavController,
    viewModel: MyViewModel
) {
    if(viewModel.inSession) {
        navController.navigate(Screen.SessionSummary.route) {
            popUpTo(Screen.SessionSummary.route) { inclusive = true }
        }
        viewModel.onCameraSessionCompleted()
    }
    viewModel.inSession = false
}

@Composable
fun CameraPreview(
    controller: LifecycleCameraController,
    modifier: Modifier = Modifier
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    AndroidView(
        factory = {
            PreviewView(it).apply {
                this.controller = controller
                controller.bindToLifecycle(lifecycleOwner)
            }
        },
        modifier = modifier
    )
}


