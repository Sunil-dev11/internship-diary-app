package com.example.internshipdiary.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColors = darkColorScheme(
    primary = Color(0xFF2962FF),
    secondary = Color(0xFF6A8DFF),
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E)
)

private val LightColors = lightColorScheme(
    primary = Color(0xFF2962FF),
    secondary = Color(0xFF6A8DFF),
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFF5F7FF)
)

@Composable
fun InternshipDiaryTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}