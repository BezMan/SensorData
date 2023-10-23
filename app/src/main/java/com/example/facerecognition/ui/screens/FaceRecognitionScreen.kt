package com.example.facerecognition.ui.screens


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import java.io.File
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

@Composable
fun FaceRecognitionScreen(
    navController: NavController,
    myViewModel: MyViewModel
) {
    val context = LocalContext.current
    val cameraExecutor = rememberCoroutineScope()

    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_CAPTURE)
        }
    }
    controller.cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

    // State to track the number of photos taken
    var photosTaken by remember { mutableStateOf(0) }

    // Set up the image capture use case
    val imageCapture = remember {
        ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .build()
    }
    val numPhotosToCapture = 30
    val interval = 1000L

    // Start the camera capture session when the screen is first composed
    LaunchedEffect(Unit) {
        val handler = Handler(Looper.getMainLooper())
        val photosTaken = AtomicInteger(0)

        val runnable = object : Runnable {
            override fun run() {
                if (photosTaken.get() < numPhotosToCapture) {
                    captureImage(context, controller, imageCapture) { capturedBitmap ->
                        runFaceContourDetection(capturedBitmap)
                    }
                    photosTaken.incrementAndGet()
                    handler.postDelayed(this, interval)
                }
            }
        }
        handler.postDelayed(runnable, interval)
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

fun captureImage(
    context: Context,
    controller: LifecycleCameraController,
    imageCapture: ImageCapture,
    onPhotoTaken: (Bitmap) -> Unit
) {
    val outputFile = File(context.cacheDir, "temp_image.jpg")
    val outputOptions = ImageCapture.OutputFileOptions.Builder(outputFile).build()
    imageCapture.takePicture(
        outputOptions,
        Executors.newSingleThreadExecutor(),
        object : ImageCapture.OnImageSavedCallback {
            override fun onError(exc: ImageCaptureException) {
                // Handle image capture error
                exc.printStackTrace()
            }

            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                val savedImageFile = output.savedUri
                val bitmap = BitmapFactory.decodeFile(savedImageFile?.path)
                onPhotoTaken(bitmap)
            }
        }
    )
}

fun runFaceContourDetection(bmp: Bitmap) {
    val image = InputImage.fromBitmap(bmp, 0)
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
            OnFailureListener { e ->
                e.printStackTrace()
            })
}

fun processFaceContourDetectionResult(faces: List<Face>) {
    if (faces.isEmpty()) {
        // Handle no face found
    }
}
