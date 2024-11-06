// File: screens/start/StartScreen.kt
package com.ayoadvisors.shadowpulse.screens.start

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ayoadvisors.shadowpulse.util.permissions.PermissionType

@Composable
fun StartScreen(
    onNavigateToLiveHome: () -> Unit,
    viewModel: StartViewModel
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.checkPermissions() // Changed from requestPermissions to checkPermissions
    }

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
                Text(
                    text = when (mode) {
                        TravelMode.CAR -> "Car"
                        TravelMode.PUBLIC_TRANSPORT -> "Transit"
                        TravelMode.WALKING -> "Walk"
                    }
                )
            }
        }
    }
}

@Composable
private fun PermissionsSection(
    permissions: Map<PermissionType, Boolean>,
    onPermissionToggle: (PermissionType) -> Unit
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
                Column(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = permission.displayName,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = permission.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
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