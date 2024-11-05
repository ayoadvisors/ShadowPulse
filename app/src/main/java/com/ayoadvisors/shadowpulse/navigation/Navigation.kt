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
fun NavigationDots(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

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
fun NavigationDot(
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
                // LoginScreen will be implemented next
            }
            composable(Screen.Start.route) {
                // StartScreen will be implemented
            }
            composable(Screen.LiveHome.route) {
                // LiveHomeScreen will be implemented
            }
            composable(Screen.LiveMap.route) {
                // LiveMapScreen will be implemented
            }
            composable(Screen.LiveLog.route) {
                // LiveLogScreen will be implemented
            }
            composable(Screen.HistoricalMap.route) {
                // HistoricalMapScreen will be implemented
            }
            composable(Screen.HistoricalLog.route) {
                // HistoricalLogScreen will be implemented
            }
            composable(Screen.Export.route) {
                // ExportScreen will be implemented
            }
            composable(Screen.Chat.route) {
                // ChatScreen will be implemented
            }
        }

        NavigationDots(
            navController = navController,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}