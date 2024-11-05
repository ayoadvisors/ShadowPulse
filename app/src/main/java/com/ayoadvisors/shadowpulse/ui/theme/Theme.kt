package com.ayoadvisors.shadowpulse.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape

// Brand Colors extracted from logo
val ShadowNavy = Color(0xFF1B1464)      // Deep navy from hexagon
val PulseCyan = Color(0xFF2BC9ED)       // Bright cyan from circle
val PureWhite = Color(0xFFFFFFFF)       // White from pulse wave
val BackgroundLight = Color(0xFFF8F9FA)  // Light background
val BackgroundDark = Color(0xFF121212)   // Dark background

// Extended color palette
val NavyLight = Color(0xFF2C3E94)       // Lighter navy for accents
val CyanDark = Color(0xFF1BA4C2)        // Darker cyan for contrast
val AlertRed = Color(0xFFE74C3C)        // For error states
val SuccessGreen = Color(0xFF2ECC71)    // For success states
val NeutralGray = Color(0xFF95A5A6)     // For inactive states

// Dark Theme Colors
private val DarkColorScheme = darkColorScheme(
    primary = PulseCyan,
    onPrimary = ShadowNavy,
    secondary = NavyLight,
    onSecondary = PureWhite,
    tertiary = CyanDark,
    onTertiary = PureWhite,
    error = AlertRed,
    background = BackgroundDark,
    surface = ShadowNavy,
    onBackground = PureWhite,
    onSurface = PureWhite
)

// Light Theme Colors
private val LightColorScheme = lightColorScheme(
    primary = ShadowNavy,
    onPrimary = PureWhite,
    secondary = PulseCyan,
    onSecondary = ShadowNavy,
    tertiary = NavyLight,
    onTertiary = PureWhite,
    error = AlertRed,
    background = BackgroundLight,
    surface = PureWhite,
    onBackground = ShadowNavy,
    onSurface = ShadowNavy
)

// Typography with a modern, technical feel
val Typography = androidx.compose.material3.Typography(
    // Large headlines for main screens
    headlineLarge = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = (-0.5).sp
    ),
    // Medium headlines for sections
    headlineMedium = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.sp
    ),
    // Large titles for important UI elements
    titleLarge = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.sp
    ),
    // Body text for main content
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.15.sp
    ),
    // Medium body text for secondary content
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.25.sp
    ),
    // Labels for buttons and small UI elements
    labelMedium = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.5.sp
    )
)

// Shapes based on logo's hexagonal design
val Shapes = androidx.compose.material3.Shapes(
    // Small components (buttons, cards)
    small = RoundedCornerShape(4.dp),
    // Medium components (dialogs, larger cards)
    medium = RoundedCornerShape(8.dp),
    // Large components (bottom sheets, modal dialogs)
    large = RoundedCornerShape(12.dp),
    // Extra large components matching logo's hexagon
    extraLarge = RoundedCornerShape(24.dp)
)

// Common spacing and dimensions
object Dimensions {
    val paddingExtraSmall = 4.dp
    val paddingSmall = 8.dp
    val paddingMedium = 16.dp
    val paddingLarge = 24.dp
    val paddingExtraLarge = 32.dp

    val iconSizeSmall = 16.dp
    val iconSizeMedium = 24.dp
    val iconSizeLarge = 32.dp

    val buttonHeight = 48.dp
    val inputFieldHeight = 56.dp

    val cardElevation = 4.dp
    val dialogElevation = 8.dp
}

@Composable
fun ShadowPulseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}