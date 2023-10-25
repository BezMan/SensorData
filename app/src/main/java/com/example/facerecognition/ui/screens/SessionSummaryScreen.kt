package com.example.facerecognition.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.facerecognition.domain.model.ExportModel
import com.example.facerecognition.presentation.MyViewModel
import com.example.facerecognition.utils.MyUtils
import java.io.File

@Composable
fun SessionSummaryScreen(
    viewModel: MyViewModel,
    onDoneClicked: () -> Unit
) {
    val dataList = viewModel.getDataList()
    val fileExportState = viewModel.fileExportState
    val context = LocalContext.current

    LaunchedEffect(key1 = fileExportState){
        if(fileExportState.isShareDataClicked){
            val uri = FileProvider.getUriForFile(
                context,
                context.applicationContext.packageName+".provider",
                File(fileExportState.shareDataUri!!)
            )
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/csv"
            intent.putExtra(Intent.EXTRA_SUBJECT,"My Export Data")
            intent.putExtra(Intent.EXTRA_STREAM,uri)

            val chooser = Intent.createChooser(intent,"Share With")
            ContextCompat.startActivity(
                context,chooser,null
            )
            viewModel.onShareDataOpen()
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
                onClick = { onDoneClicked() },
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
    return sessionData.filter { MyUtils.isLightInRange(it.sensorData) }.size.toString()
}

fun calculateFaceNotDetectedDuration(sessionData: List<ExportModel>): String {
    // Implement the logic to calculate face not detected duration
    return sessionData.filter { !MyUtils.isLightInRange(it.sensorData) }.size.toString()
}
