package com.tamaturgo.focca.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val OnEmberText = Color(0xFF1A0E08)

// Dark-first, no light mode in the MVP (PRD 7.1) — Material You dynamic color is
// intentionally not offered here: it would replace this palette with one derived
// from the device wallpaper, breaking the fixed, job-based color system (7.2).
private val FoccaColorScheme = darkColorScheme(
    primary = FoccaEmber,
    onPrimary = OnEmberText,
    secondary = FoccaGold,
    onSecondary = OnEmberText,
    tertiary = FoccaPulse,
    onTertiary = Color(0xFF062622),
    background = FoccaBg,
    onBackground = FoccaText,
    surface = FoccaSurface,
    onSurface = FoccaText,
    surfaceVariant = FoccaSurface2,
    onSurfaceVariant = FoccaTextMuted,
    surfaceContainer = FoccaSurface,
    surfaceContainerHigh = FoccaSurface2,
    surfaceContainerHighest = FoccaSurface3,
    outline = FoccaLine,
    outlineVariant = FoccaLine
)

@Composable
fun FoccaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = FoccaColorScheme,
        typography = Typography,
        content = content
    )
}
