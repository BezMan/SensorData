package com.example.facerecognition.ui.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.facerecognition.presentation.MyViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import kotlinx.coroutines.delay

@Composable
fun FaceRecognitionScreen(
    navController: NavController,
    myViewModel: MyViewModel
) {
    val cameraExecutor = rememberCoroutineScope()

    val controller = remember {
        LifecycleCameraController(navController.context).apply {
            setEnabledUseCases(CameraController.IMAGE_CAPTURE)
        }
    }
    controller.cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

    // State to track the number of photos taken
    var photosTaken by remember { mutableStateOf(0) }

    // Start the camera capture session when the screen is first composed
    LaunchedEffect(Unit) {
//        while (photosTaken < 30) {
//            val timestamp = System.currentTimeMillis().toString()
//            takePhoto(
//                navController.context,
//                controller = controller
//            ) { capturedBitmap ->
//                runFaceContourDetection(capturedBitmap)
////                val isFaceRecognized = processFaceContourDetectionResult(capturedBitmap)
////                myViewModel.onCameraPhotoCaptured(timestamp, isFaceRecognized)
//            }
//            photosTaken++
//            delay(1000) // Capture a photo every second
//        }
        myViewModel.onCameraSessionCompleted()
        // Once 30 photos are taken, you can navigate or perform other actions.
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
                myViewModel.onCameraSessionCompleted()
                // Add navigation or other actions
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
private fun runFaceContourDetection(bmp: Bitmap) {
    val image = InputImage.fromBitmap(bmp, 0)
//    val image = InputImage.fromBitmap(Bitmap.createBitmap(1,1,Bitmap.Config.ALPHA_8), 0)
    val options = FaceDetectorOptions.Builder()
        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
        .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
        .build()
    val detector: FaceDetector = FaceDetection.getClient(options)
    detector.process(image)
        .addOnSuccessListener { faces ->
            processFaceContourDetectionResult(faces)
        }
        .addOnFailureListener(
            OnFailureListener { e -> // Task failed with an exception
                e.printStackTrace()
            })
}

private fun processFaceContourDetectionResult(faces: List<Face>) {
    // Task completed successfully
    if (faces.isEmpty()) {
//        showToast("No face found")
//        return
    }
}

private fun takePhoto(
    context: Context,
    controller: LifecycleCameraController,
    onPhotoTaken: (Bitmap) -> Unit
) {
    controller.takePicture(
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                super.onCaptureSuccess(image)

                val matrix = Matrix().apply {
                    postRotate(image.imageInfo.rotationDegrees.toFloat())
                }
                val rotatedBitmap = Bitmap.createBitmap(
                    image.toBitmap(),
                    0,
                    0,
                    image.width,
                    image.height,
                    matrix,
                    true
                )

                // Call the callback with the captured bitmap
                onPhotoTaken(rotatedBitmap)

                // Close the image proxy to release resources
                image.close()
            }

            override fun onError(exception: ImageCaptureException) {
                super.onError(exception)
                Log.e("Camera", "Couldn't take photo: ", exception)
            }
        }
    )
}
