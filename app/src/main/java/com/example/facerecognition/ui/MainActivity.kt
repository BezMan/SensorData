package com.example.facerecognition.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.facerecognition.ui.navigation.AppNavigation
import com.example.facerecognition.ui.theme.FaceRecognitionTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FaceRecognitionTheme {
                AppNavigation()
            }
        }
    }
}
