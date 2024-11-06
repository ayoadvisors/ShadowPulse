// File: MainActivity.kt
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
import com.ayoadvisors.shadowpulse.util.permissions.PermissionManager
import com.ayoadvisors.shadowpulse.util.permissions.PermissionHandler

class MainActivity : ComponentActivity() {
    private lateinit var permissionManager: PermissionManager
    private lateinit var permissionHandler: PermissionHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize permission management
        permissionManager = PermissionManager(this)
        permissionHandler = PermissionHandler(this, permissionManager)
        lifecycle.addObserver(permissionHandler)

        setContent {
            ShadowPulseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    ShadowPulseNavigation(
                        navController = navController,
                        permissionManager = permissionManager
                    )
                }
            }
        }
    }
}