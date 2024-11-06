// File: screens/start/StartViewModelFactory.kt
package com.ayoadvisors.shadowpulse.screens.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ayoadvisors.shadowpulse.util.permissions.PermissionManager

class StartViewModelFactory(
    private val permissionManager: PermissionManager
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StartViewModel::class.java)) {
            return StartViewModel(permissionManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}