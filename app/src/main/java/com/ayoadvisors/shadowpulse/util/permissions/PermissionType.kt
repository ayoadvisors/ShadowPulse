// File: util/permissions/PermissionType.kt
package com.ayoadvisors.shadowpulse.util.permissions

import android.Manifest
import android.os.Build

enum class PermissionType(
    val permissionName: String,
    val displayName: String,
    val description: String
) {
    LOCATION(
        permissionName = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Manifest.permission.ACCESS_FINE_LOCATION
        } else {
            Manifest.permission.ACCESS_COARSE_LOCATION
        },
        displayName = "Location",
        description = "Required for navigation and device tracking"
    ),
    WIFI(
        permissionName = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Manifest.permission.ACCESS_FINE_LOCATION
        } else {
            Manifest.permission.ACCESS_WIFI_STATE
        },
        displayName = "WiFi",
        description = "Required for detecting nearby WiFi devices"
    ),
    BLUETOOTH(
        permissionName = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Manifest.permission.BLUETOOTH_SCAN
        } else {
            Manifest.permission.BLUETOOTH
        },
        displayName = "Bluetooth",
        description = "Required for detecting nearby Bluetooth devices"
    ),
    BACKGROUND_LOCATION(
        permissionName = Manifest.permission.ACCESS_BACKGROUND_LOCATION,
        displayName = "Background Location",
        description = "Required for continuous device tracking"
    );

    companion object {
        fun getAllRequiredPermissions(): List<String> {
            return values().map { it.permissionName }.distinct()
        }
    }
}