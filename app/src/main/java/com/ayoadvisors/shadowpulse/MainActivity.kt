package com.ayoadvisors.shadowpulse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.ayoadvisors.shadowpulse.navigation.ShadowPulseNavigation
import com.ayoadvisors.shadowpulse.ui.theme.ShadowPulseTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val systemUiController = rememberSystemUiController()

            ShadowPulseTheme {
                // Update status bar color based on theme
                systemUiController.setSystemBarsColor(
                    color = MaterialTheme.colorScheme.background
                )

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShadowPulseNavigation(navController = navController)
                }
            }
        }
    }
}