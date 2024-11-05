package com.ayoadvisors.shadowpulse.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ayoadvisors.shadowpulse.screens.login.LoginScreen
import com.ayoadvisors.shadowpulse.ui.theme.Dimensions

sealed class Screen(
    val route: String,
    val title: String,
    val showInBottomNav: Boolean = true
) {
    object Login : Screen("login", "Login", false)
    object Start : Screen("start", "Start", false)
    object LiveHome : Screen("live_home", "Live Home")
    object LiveMap : Screen("live_map", "Live Map")
    object LiveLog : Screen("live_log", "Live Log")
    object HistoricalMap : Screen("historical_map", "Historical Map")
    object HistoricalLog : Screen("historical_log", "Historical Log")
    object Export : Screen("export", "Export")
    object Chat : Screen("chat", "Chat")

    companion object {
        fun mainScreens() = listOf(
            LiveHome, LiveMap, LiveLog, HistoricalMap,
            HistoricalLog, Export, Chat
        )
    }
}

@Composable
fun ShadowPulseNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = Screen.Login.route
) {
    Box(modifier = modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.fillMaxSize()
        ) {
            composable(Screen.Login.route) {
                LoginScreen(
                    onNavigateToStart = {
                        navController.navigate(Screen.Start.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                )
            }
            composable(Screen.Start.route) {
                // StartScreen will be implemented next
                // For now, we'll leave this empty
            }
            composable(Screen.LiveHome.route) {
                // Will be implemented later
            }
            composable(Screen.LiveMap.route) {
                // Will be implemented later
            }
            composable(Screen.LiveLog.route) {
                // Will be implemented later
            }
            composable(Screen.HistoricalMap.route) {
                // Will be implemented later
            }
            composable(Screen.HistoricalLog.route) {
                // Will be implemented later
            }
            composable(Screen.Export.route) {
                // Will be implemented later
            }
            composable(Screen.Chat.route) {
                // Will be implemented later
            }
        }

        NavigationDots(
            navController = navController,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun NavigationDots(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Only show dots for main screens (not login or start)
    if (currentRoute == Screen.Login.route || currentRoute == Screen.Start.route) {
        return
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = Dimensions.paddingMedium),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Screen.mainScreens().forEach { screen ->
            NavigationDot(
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
            if (screen != Screen.mainScreens().last()) {
                Spacer(modifier = Modifier.width(Dimensions.paddingSmall))
            }
        }
    }
}

@Composable
private fun NavigationDot(
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(8.dp)
            .clip(CircleShape)
            .background(
                if (selected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
            )
            .clickable(onClick = onClick)
    )
}