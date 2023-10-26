package com.example.sensordata.ui.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import com.example.sensordata.domain.model.ExportModel
import com.example.sensordata.presentation.FileExportState
import com.example.sensordata.presentation.MyViewModel
import com.example.sensordata.ui.navigation.Screen
import com.example.sensordata.ui.theme.Pink80
import com.example.sensordata.utils.SensorUtils
import java.io.File

@Composable
fun SessionSummaryScreen(navController: NavController, viewModel: MyViewModel) {

    val dataList = viewModel.getDataList()
    val fileExportState = viewModel.fileExportState
    val context = LocalContext.current

    LaunchedEffect(key1 = fileExportState) {
        if (fileExportState.isShareDataClicked) {
            openShareIntentChooser(context, fileExportState, viewModel)
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
            Text(text = "Session Summary")

            // Display session duration, face detected duration, and face not detected duration
            Text(text = "Session Duration: ${calculateSessionDuration(dataList)}")
            Text(text = "Face Detected Duration: ${calculateFaceDetectedDuration(dataList)}")
            Text(text = "Face Not Detected Duration: ${calculateFaceNotDetectedDuration(dataList)}")

            Button(
                onClick = { onDoneClicked(navController) },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Done")
            }

            Button(
                onClick = { onShareCsvClicked(viewModel) },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Share CSV")
            }
        }
    }
    if (fileExportState.isGeneratingLoading) {
        LoadingComposable(fileExportState)
    }

}

fun onDoneClicked(navController: NavController) {
    navController.navigate(Screen.Welcome.route) {
        popUpTo(Screen.Welcome.route) { inclusive = true }
    }

}


private fun openShareIntentChooser(
    context: Context,
    fileExportState: FileExportState,
    viewModel: MyViewModel
) {
    val uri = FileProvider.getUriForFile(
        context,
        context.applicationContext.packageName + ".provider",
        File(fileExportState.shareDataUri!!)
    )
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/csv"
    intent.putExtra(Intent.EXTRA_SUBJECT, "My Export Data")
    intent.putExtra(Intent.EXTRA_STREAM, uri)

    val chooser = Intent.createChooser(intent, "Share With")
    ContextCompat.startActivity(
        context, chooser, null
    )
    viewModel.onShareDataOpen()
}

@Composable
private fun LoadingComposable(fileExportState: FileExportState) {
    Dialog(
        onDismissRequest = {}
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                color = Pink80
            )
            Text(
                "Generating File (${fileExportState.generatingProgress}%) ...",
                color = Pink80,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

fun onShareCsvClicked(viewModel: MyViewModel) {
    viewModel.generateExportFile()
}

// Implement these functions to calculate durations based on your session data
fun calculateSessionDuration(sessionData: List<ExportModel>): String {
    // Implement the logic to calculate session duration
    return sessionData.size.toString()
}

fun calculateFaceDetectedDuration(sessionData: List<ExportModel>): String {
    // Implement the logic to calculate face detected duration
    return sessionData.filter { SensorUtils.isLightInRange(it.sensorData) }.size.toString()
}

fun calculateFaceNotDetectedDuration(sessionData: List<ExportModel>): String {
    // Implement the logic to calculate face not detected duration
    return sessionData.filter { !SensorUtils.isLightInRange(it.sensorData) }.size.toString()
}
