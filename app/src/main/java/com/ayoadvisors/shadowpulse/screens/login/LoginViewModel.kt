package com.ayoadvisors.shadowpulse.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.UsernameChanged -> {
                _state.update { it.copy(
                    username = event.username,
                    error = null
                ) }
            }
            is LoginEvent.PasswordChanged -> {
                _state.update { it.copy(
                    password = event.password,
                    error = null
                ) }
            }
            LoginEvent.LoginClicked -> {
                attemptLogin()
            }
            LoginEvent.SignUpClicked -> {
                // Handle sign up navigation
            }
            LoginEvent.ForgotPasswordClicked -> {
                // Handle forgot password
            }
            LoginEvent.BiometricLoginClicked -> {
                _state.update { it.copy(showBiometricPrompt = true) }
            }
            LoginEvent.BiometricAuthenticationSucceeded -> {
                handleBiometricSuccess()
            }
            LoginEvent.BiometricAuthenticationFailed -> {
                _state.update { it.copy(
                    error = "Biometric authentication failed",
                    showBiometricPrompt = false
                ) }
            }
        }
    }

    private fun attemptLogin() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            // TODO: Implement actual login logic
            // This is a placeholder for demonstration
            if (_state.value.username.isBlank() || _state.value.password.isBlank()) {
                _state.update { it.copy(
                    error = "Username and password are required",
                    isLoading = false
                ) }
                return@launch
            }

            // Simulate network delay
            kotlinx.coroutines.delay(1000)

            _state.update { it.copy(
                isAuthenticated = true,
                isLoading = false
            ) }
        }
    }

    private fun handleBiometricSuccess() {
        viewModelScope.launch {
            _state.update { it.copy(
                isAuthenticated = true,
                showBiometricPrompt = false
            ) }
        }
    }
}