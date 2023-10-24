package com.example.facerecognition.ui.screens


import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
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
import androidx.compose.runtime.mutableStateOf
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
    myViewModel: MyViewModel
) {
    val context = LocalContext.current

    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_CAPTURE or CameraController.VIDEO_CAPTURE)
        }
    }
    controller.cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

    // State to track the number of photos taken
    var taken by remember { mutableStateOf(0) }


    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

    DisposableEffect(lightSensor) {
        val listener = object : SensorEventListener {
            private var lastEventTime = 0L

            override fun onSensorChanged(event: SensorEvent?) {
                // Get the current time
                val currentTime = System.currentTimeMillis()

                // Check if the last event was more than 1000 ms ago
                if (currentTime - lastEventTime >= 1000L) {
                    // Get the light sensor Lux value
                    val lux = event?.values?.get(0) ?: 0f


                    lastEventTime = currentTime
                    // Do something with the light sensor Lux value
                    Log.d("zzzzz", lux.toString())

                    FileData(currentTime.toString(), MyUtils.isLightInRange(lux))
                }

            }

            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
            }

        }

        sensorManager.registerListener(listener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)

        onDispose {
            sensorManager.unregisterListener(listener)
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

        // Your UI elements, and a "Complete Session" button
        Button(
            onClick = {
                navController.navigate(Screen.SessionSummary.route) {
                    popUpTo(Screen.SessionSummary.route) { inclusive = true }
                }
                myViewModel.onCameraSessionCompleted()
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Text("Complete Session")
        }
    }
}

@Composable
fun CameraPreview(
    controller: LifecycleCameraController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
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


