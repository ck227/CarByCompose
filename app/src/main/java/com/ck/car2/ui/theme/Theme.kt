/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ck.car2.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightColorPalette = CarByComposeColors(
    is_dark = false,
    home_bottom_item_selected = color_home_bottom_item_selected,
    home_bottom_item_un_selected = color_home_bottom_item_un_selected,
    ui_background = color_ui_background,
    home_bottom_item_bg = color_home_bottom_item_bg,
)

private val DarkColorPalette = CarByComposeColors(
    is_dark = true,
    home_bottom_item_selected = color_home_bottom_item_selected,
    home_bottom_item_un_selected = color_home_bottom_item_un_selected,
    ui_background = color_ui_background,
    home_bottom_item_bg = color_home_bottom_item_bg,
)

@Composable
fun CarByComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette
    val sysUiController = rememberSystemUiController()
    SideEffect {
        sysUiController.setSystemBarsColor(
            color = colors.uiBackground.copy(alpha = AlphaNearOpaque)
        )
        sysUiController.setStatusBarColor(
            color = colors.uiBackground.copy(alpha = AlphaNearOpaque)
        )
    }

    ProvideCarByComposeColors(colors) {
        androidx.compose.material.MaterialTheme(
            colors = debugColors(darkTheme),
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

object CarByComposeTheme {
    val colors: CarByComposeColors
        @Composable get() = LocalCarByComposeColors.current
}

@Stable
class CarByComposeColors(
    is_dark: Boolean,
    home_bottom_item_selected: Color,
    home_bottom_item_un_selected: Color,
    ui_background: Color,
    home_bottom_item_bg: Color
) {
    var isDark by mutableStateOf(is_dark)
    var homeBottomItemSelected by mutableStateOf(home_bottom_item_selected)
    var homeBottomItemUnSelected by mutableStateOf(home_bottom_item_un_selected)
    var uiBackground by mutableStateOf(ui_background)
    var homeBottomItemBg by mutableStateOf(home_bottom_item_bg)

    fun update(other: CarByComposeColors) {
        isDark = other.isDark
        homeBottomItemSelected = other.homeBottomItemSelected
        homeBottomItemUnSelected = other.homeBottomItemUnSelected
        uiBackground = other.uiBackground
        homeBottomItemBg = other.homeBottomItemBg
    }

    fun copy(): CarByComposeColors = CarByComposeColors(
        is_dark = isDark,
        home_bottom_item_selected = homeBottomItemSelected,
        home_bottom_item_un_selected = homeBottomItemUnSelected,
        ui_background = uiBackground,
        home_bottom_item_bg = homeBottomItemBg,
    )
}


@Composable
fun ProvideCarByComposeColors(
    colors: CarByComposeColors, content: @Composable () -> Unit
) {
    val colorPalette = remember {
        // Explicitly creating a new object here so we don't mutate the initial [colors]
        // provided, and overwrite the values set in it.
        colors.copy()
    }
    colorPalette.update(colors)
    CompositionLocalProvider(LocalCarByComposeColors provides colorPalette, content = content)
}

private val LocalCarByComposeColors = staticCompositionLocalOf<CarByComposeColors> {
    error("No CarByComposeColorPalette provided")
}

fun debugColors(
    darkTheme: Boolean, debugColor: Color = Color.Magenta
) = Colors(
    primary = debugColor,
    primaryVariant = debugColor,
    secondary = debugColor,
    secondaryVariant = debugColor,
    background = debugColor,
    surface = debugColor,
    error = debugColor,
    onPrimary = debugColor,
    onSecondary = debugColor,
    onBackground = debugColor,
    onSurface = debugColor,
    onError = debugColor,
    isLight = !darkTheme
)


/*private val LightColors = lightColorScheme(
    primary = md_theme_light_primary,
//    onPrimary = md_theme_light_onPrimary,
//    primaryContainer = md_theme_light_primaryContainer,
//    onPrimaryContainer = md_theme_light_onPrimaryContainer,
//    secondary = md_theme_light_secondary,
//    onSecondary = md_theme_light_onSecondary,
//    secondaryContainer = md_theme_light_secondaryContainer,
//    onSecondaryContainer = md_theme_light_onSecondaryContainer,
//    tertiary = md_theme_light_tertiary,
//    onTertiary = md_theme_light_onTertiary,
//    tertiaryContainer = md_theme_light_tertiaryContainer,
//    onTertiaryContainer = md_theme_light_onTertiaryContainer,
//    error = md_theme_light_error,
//    errorContainer = md_theme_light_errorContainer,
//    onError = md_theme_light_onError,
//    onErrorContainer = md_theme_light_onErrorContainer,
//    background = md_theme_light_background,
//    onBackground = md_theme_light_onBackground,
//    surface = md_theme_light_surface,
//    onSurface = md_theme_light_onSurface,
//    surfaceVariant = md_theme_light_surfaceVariant,
//    onSurfaceVariant = md_theme_light_onSurfaceVariant,
//    outline = md_theme_light_outline,
//    inverseOnSurface = md_theme_light_inverseOnSurface,
//    inverseSurface = md_theme_light_inverseSurface,
//    inversePrimary = md_theme_light_inversePrimary,
//    surfaceTint = md_theme_light_surfaceTint,
)

private val DarkColors = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inverseSurface = md_theme_dark_inverseSurface,
    inversePrimary = md_theme_dark_inversePrimary,
    surfaceTint = md_theme_dark_surfaceTint,
)

@Composable
fun CarByComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme =
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        } else {
        if (darkTheme) DarkColors else LightColors
//        }

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = CarByComposeShapes,
        typography = CarByComposeTypography,
        content = content
    )
}*/
