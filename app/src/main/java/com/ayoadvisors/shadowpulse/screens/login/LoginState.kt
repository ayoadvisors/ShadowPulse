package com.ayoadvisors.shadowpulse.screens.login

data class LoginState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isAuthenticated: Boolean = false,
    val showBiometricPrompt: Boolean = false,
    val biometricEnabled: Boolean = false
)