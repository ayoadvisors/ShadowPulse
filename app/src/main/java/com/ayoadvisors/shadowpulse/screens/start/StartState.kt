// File: screens/start/StartState.kt
package com.ayoadvisors.shadowpulse.screens.start

import com.ayoadvisors.shadowpulse.util.permissions.PermissionType

data class StartState(
    val fromLocation: String = "",
    val toLocation: String = "",
    val travelMode: TravelMode = TravelMode.CAR,
    val permissions: Map<PermissionType, Boolean> = PermissionType.values().associateWith { false },
    val canStartNavigation: Boolean = false
)