package com.ayoadvisors.shadowpulse.screens.login

sealed class LoginEvent {
    data class UsernameChanged(val username: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    data object LoginClicked : LoginEvent()
    data object SignUpClicked : LoginEvent()
    data object ForgotPasswordClicked : LoginEvent()
    data object BiometricLoginClicked : LoginEvent()
    data object BiometricAuthenticationSucceeded : LoginEvent()
    data object BiometricAuthenticationFailed : LoginEvent()
}