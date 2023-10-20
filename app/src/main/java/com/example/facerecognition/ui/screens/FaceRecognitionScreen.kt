package com.example.facerecognition.ui.screens

import androidx.camera.core.ImageCapture
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
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
    imageCapture: ImageCapture,
    faceDetector: FaceDetector,
    myViewModel: MyViewModel
) {
    val cameraExecutor = rememberCoroutineScope()

    val preview = rememberUpdatedState(imageCapture)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Display the camera preview
        CameraPreview(
            modifier = Modifier.fillMaxSize(),
            imageCapture = imageCapture
        )

        // Face detection and UI updates
        LaunchedEffect(Unit) {
            while (true) {
                val imageCaptureResult = captureImage(imageCapture, cameraExecutor)
                val faces = runFaceContourDetection(imageCaptureResult, faceDetector)
                myViewModel.processFaceContourDetectionResult(faces)
                delay(1000)
            }
        }

        // Your UI elements and face recognition results here
    }
}

private fun runFaceContourDetection() {
    val image = InputImage.fromBitmap(mSelectedImage, 0)
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
