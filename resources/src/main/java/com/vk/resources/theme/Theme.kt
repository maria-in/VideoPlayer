package com.vk.resources.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.vk.resources.theme.color.DarkGray
import com.vk.resources.theme.color.FadedGreen
import com.vk.resources.theme.color.LightGray
import com.vk.resources.theme.color.PrimaryGreen

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryGreen,
    secondary = DarkGray,
    tertiary = FadedGreen,
    tertiaryContainer = LightGray,
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryGreen,
    secondary = DarkGray,
    tertiary = FadedGreen,
    tertiaryContainer = LightGray,
)

@Composable
fun VideoPlayerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}