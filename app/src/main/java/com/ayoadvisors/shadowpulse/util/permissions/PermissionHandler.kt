// File: util/permissions/PermissionHandler.kt
package com.ayoadvisors.shadowpulse.util.permissions

import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class PermissionHandler(
    private val activity: ComponentActivity,
    private val permissionManager: PermissionManager
) : DefaultLifecycleObserver {

    private val permissionLauncher = activity.registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissions.entries.forEach { (permission, isGranted) ->
            PermissionType.values()
                .find { it.permissionName == permission }
                ?.let { permissionType ->
                    permissionManager.updatePermissionState(permissionType)
                }
        }
    }

    fun requestPermissions() {
        val permissions = PermissionType.getAllRequiredPermissions().toTypedArray()
        permissionLauncher.launch(permissions)
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        permissionManager.updateAllPermissionStates()
    }
}