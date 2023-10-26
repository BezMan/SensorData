package com.example.sensordata.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.sensordata.ui.navigation.AppNavigation
import com.example.sensordata.ui.theme.SensorDataTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SensorDataTheme {
                AppNavigation()
            }
        }
    }
}
