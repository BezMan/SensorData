package com.example.facerecognition.ui.screens

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

@Composable
fun WelcomeScreen(navController: NavController, viewModel: MyViewModel) {
    val context = LocalContext.current

    // Request camera permissions
    val activityResultLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val permissionGranted = permissions.entries.all { it.value }

        if (permissionGranted) {
            val isLightConditionSuitable = // Implement your light sensor check logic here
                if (isLightConditionSuitable) {
                    // Navigate to the appropriate screen when light conditions are suitable
                    navController.navigate(Screen.FaceRecognition.route)
                } else {
                    // Navigate to the "LightSensorScreen" when light conditions are not suitable
                    navController.navigate(Screen.LightSensor.route)
                }
        } else {
            Toast.makeText(context, "Permission request denied", Toast.LENGTH_SHORT).show()
        }
    }

    viewModel.requestPermissions(activityResultLauncher)

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
                    // Check for camera permissions and request if needed
                    if (viewModel.allPermissionsGranted()) {
                        viewModel.startCamera()
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
