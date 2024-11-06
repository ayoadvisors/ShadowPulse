// File: screens/start/StartScreen.kt
package com.ayoadvisors.shadowpulse.screens.start

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun StartScreen(
    onNavigateToLiveHome: () -> Unit,
    viewModel: StartViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LocationInputSection(
            fromLocation = state.fromLocation,
            toLocation = state.toLocation,
            onFromLocationChange = { viewModel.onEvent(StartEvent.FromLocationChanged(it)) },
            onToLocationChange = { viewModel.onEvent(StartEvent.ToLocationChanged(it)) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TravelModeSection(
            selectedMode = state.travelMode,
            onModeSelected = { viewModel.onEvent(StartEvent.TravelModeChanged(it)) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        PermissionsSection(
            permissions = state.permissions,
            onPermissionToggle = { permission ->
                viewModel.onEvent(StartEvent.PermissionToggled(permission))
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        NavigationButtons(
            isStartEnabled = state.canStartNavigation,
            onStartClick = {
                viewModel.onEvent(StartEvent.StartNavigationClicked)
                onNavigateToLiveHome()
            },
            onStopClick = { viewModel.onEvent(StartEvent.StopNavigationClicked) }
        )
    }
}

@Composable
private fun LocationInputSection(
    fromLocation: String,
    toLocation: String,
    onFromLocationChange: (String) -> Unit,
    onToLocationChange: (String) -> Unit
) {
    OutlinedTextField(
        value = fromLocation,
        onValueChange = onFromLocationChange,
        label = { Text("From Location") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )

    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
        value = toLocation,
        onValueChange = onToLocationChange,
        label = { Text("To Location") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}

@Composable
private fun TravelModeSection(
    selectedMode: TravelMode,
    onModeSelected: (TravelMode) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        TravelMode.values().forEach { mode ->
            ElevatedButton(
                onClick = { onModeSelected(mode) },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = if (mode == selectedMode) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.surface
                    }
                )
            ) {
                Text(mode.name)
            }
        }
    }
}

@Composable
private fun PermissionsSection(
    permissions: Map<Permission, Boolean>,
    onPermissionToggle: (Permission) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Required Permissions",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        permissions.forEach { (permission, isGranted) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Switch(
                    checked = isGranted,
                    onCheckedChange = { onPermissionToggle(permission) }
                )
                Text(
                    text = permission.displayName,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun NavigationButtons(
    isStartEnabled: Boolean,
    onStartClick: () -> Unit,
    onStopClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = onStartClick,
            enabled = isStartEnabled,
            modifier = Modifier.weight(1f)
        ) {
            Text("Start Navigation")
        }

        Spacer(modifier = Modifier.width(16.dp))

        Button(
            onClick = onStopClick,
            modifier = Modifier.weight(1f)
        ) {
            Text("Stop Navigation")
        }
    }
}

// File: screens/start/StartViewModel.kt
package com.ayoadvisors.shadowpulse.screens.start

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class StartViewModel : ViewModel() {
    private val _state = MutableStateFlow(StartState())
    val state: StateFlow<StartState> = _state.asStateFlow()

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
                _state.update { currentState ->
                    val updatedPermissions = currentState.permissions.toMutableMap().apply {
                        put(event.permission, !(this[event.permission] ?: false))
                    }
                    currentState.copy(permissions = updatedPermissions)
                }
            }
            StartEvent.StartNavigationClicked -> {
                // Handle start navigation logic
            }
            StartEvent.StopNavigationClicked -> {
                // Handle stop navigation logic
            }
        }

        // Update canStartNavigation based on current state
        updateCanStartNavigation()
    }

    private fun updateCanStartNavigation() {
        _state.update { currentState ->
            currentState.copy(
                canStartNavigation = currentState.fromLocation.isNotBlank() &&
                        currentState.toLocation.isNotBlank() &&
                        currentState.permissions.values.all { it }
            )
        }
    }
}

// File: screens/start/StartState.kt
package com.ayoadvisors.shadowpulse.screens.start

data class StartState(
    val fromLocation: String = "",
    val toLocation: String = "",
    val travelMode: TravelMode = TravelMode.CAR,
    val permissions: Map<Permission, Boolean> = Permission.values().associateWith { false },
    val canStartNavigation: Boolean = false
)

// File: screens/start/StartEvent.kt
package com.ayoadvisors.shadowpulse.screens.start

sealed class StartEvent {
    data class FromLocationChanged(val location: String) : StartEvent()
    data class ToLocationChanged(val location: String) : StartEvent()
    data class TravelModeChanged(val mode: TravelMode) : StartEvent()
    data class PermissionToggled(val permission: Permission) : StartEvent()
    object StartNavigationClicked : StartEvent()
    object StopNavigationClicked : StartEvent()
}

// File: screens/start/models/TravelMode.kt
package com.ayoadvisors.shadowpulse.screens.start

enum class TravelMode {
    CAR,
    PUBLIC_TRANSPORT,
    WALKING
}

// File: screens/start/models/Permission.kt
package com.ayoadvisors.shadowpulse.screens.start

enum class Permission(val displayName: String) {
    WIFI("WiFi"),
    BLUETOOTH("Bluetooth"),
    CELLULAR("Cellular"),
    LOCATION("Location")
}