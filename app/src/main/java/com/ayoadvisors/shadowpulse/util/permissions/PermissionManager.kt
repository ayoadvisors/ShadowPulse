// File: util/permissions/PermissionManager.kt
package com.ayoadvisors.shadowpulse.util.permissions

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PermissionManager(private val context: Context) {
    private val _permissionStates = MutableStateFlow<Map<PermissionType, Boolean>>(
        PermissionType.values().associateWith { false }
    )
    val permissionStates: StateFlow<Map<PermissionType, Boolean>> = _permissionStates.asStateFlow()

    fun hasPermission(permission: PermissionType): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission.permissionName
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun updatePermissionState(permission: PermissionType) {
        _permissionStates.value = _permissionStates.value.toMutableMap().apply {
            put(permission, hasPermission(permission))
        }
    }

    fun updateAllPermissionStates() {
        _permissionStates.value = PermissionType.values().associateWith { hasPermission(it) }
    }

    fun areAllPermissionsGranted(): Boolean {
        return PermissionType.values().all { hasPermission(it) }
    }
}