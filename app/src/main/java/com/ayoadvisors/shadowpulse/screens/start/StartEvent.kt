// File: screens/start/StartEvent.kt
package com.ayoadvisors.shadowpulse.screens.start

import com.ayoadvisors.shadowpulse.util.permissions.PermissionType

sealed class StartEvent {
    data class FromLocationChanged(val location: String) : StartEvent()
    data class ToLocationChanged(val location: String) : StartEvent()
    data class TravelModeChanged(val mode: TravelMode) : StartEvent()
    data class PermissionToggled(val permission: PermissionType) : StartEvent()
    object StartNavigationClicked : StartEvent()
    object StopNavigationClicked : StartEvent()
}