package com.ayoadvisors.shadowpulse.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ayoadvisors.shadowpulse.R
import com.ayoadvisors.shadowpulse.screens.login.components.LoginButton
import com.ayoadvisors.shadowpulse.ui.theme.Dimensions

@Composable
fun LoginScreen(
    onNavigateToStart: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimensions.paddingLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Dimensions.paddingMedium)
    ) {
        // Logo
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "ShadowPulse Logo",
            modifier = Modifier.size(Dimensions.iconSizeLarge * 3)
        )

        // Username field
        OutlinedTextField(
            value = state.username,
            onValueChange = { viewModel.onEvent(LoginEvent.UsernameChanged(it)) },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        // Password field
        OutlinedTextField(
            value = state.password,
            onValueChange = { viewModel.onEvent(LoginEvent.PasswordChanged(it)) },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        // Error message
        if (state.error != null) {
            Text(
                text = state.error!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        // Login button
        LoginButton(
            onClick = { viewModel.onEvent(LoginEvent.LoginClicked) },
            isLoading = state.isLoading
        )

        // Biometric login button (if available)
        if (state.biometricEnabled) {
            TextButton(
                onClick = { viewModel.onEvent(LoginEvent.BiometricLoginClicked) }
            ) {
                Text("Login with Biometrics")
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                onClick = { viewModel.onEvent(LoginEvent.SignUpClicked) }
            ) {
                Text("Sign Up")
            }
            TextButton(
                onClick = { viewModel.onEvent(LoginEvent.ForgotPasswordClicked) }
            ) {
                Text("Forgot Password?")
            }
        }
    }

    LaunchedEffect(state.isAuthenticated) {
        if (state.isAuthenticated) {
            onNavigateToStart()
        }
    }
}