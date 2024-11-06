// File: screens/start/StartViewModel.kt
package com.ayoadvisors.shadowpulse.screens.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayoadvisors.shadowpulse.util.permissions.PermissionManager
import com.ayoadvisors.shadowpulse.util.permissions.PermissionType
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class StartViewModel(
    private val permissionManager: PermissionManager
) : ViewModel() {
    private val _state = MutableStateFlow(StartState())
    val state: StateFlow<StartState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            permissionManager.permissionStates
                .collect { permissions ->
                    _state.update { currentState ->
                        currentState.copy(
                            permissions = permissions,
                            canStartNavigation = canStartNavigation(
                                currentState.fromLocation,
                                currentState.toLocation,
                                permissions
                            )
                        )
                    }
                }
        }
    }

    fun onEvent(event: StartEvent) {
        when (event) {
            is StartEvent.FromLocationChanged -> {
                _state.update { it.copy(fromLocation = event.location) }
            }
            is StartEvent.ToLocationChanged -> {
                _state.update { it.copy(toLocation = event.location) }
            }
            is StartEvent.TravelModeChanged -> {
                _state.update { it.copy(travelMode = event.mode) }
            }
            is StartEvent.PermissionToggled -> {
                // Request the permission through PermissionHandler
            }
            StartEvent.StartNavigationClicked -> {
                if (permissionManager.areAllPermissionsGranted()) {
                    // Handle start navigation
                }
            }
            StartEvent.StopNavigationClicked -> {
                // Handle stop navigation
            }
        }
        updateCanStartNavigation()
    }

    private fun canStartNavigation(
        fromLocation: String,
        toLocation: String,
        permissions: Map<PermissionType, Boolean>
    ): Boolean {
        return fromLocation.isNotBlank() &&
                toLocation.isNotBlank() &&
                permissions.values.all { it }
    }

    private fun updateCanStartNavigation() {
        _state.update { currentState ->
            currentState.copy(
                canStartNavigation = canStartNavigation(
                    currentState.fromLocation,
                    currentState.toLocation,
                    currentState.permissions
                )
            )
        }
    }
}