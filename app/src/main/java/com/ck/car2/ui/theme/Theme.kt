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
    home_icon_text = color_home_icon_text,
)

private val DarkColorPalette = CarByComposeColors(
    is_dark = true,
    home_bottom_item_selected = color_home_bottom_item_selected,
    home_bottom_item_un_selected = color_home_bottom_item_un_selected,
    ui_background = color_ui_background,
    home_bottom_item_bg = color_home_bottom_item_bg,
    home_icon_text = color_home_icon_text,
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
    home_bottom_item_bg: Color,
    home_icon_text: Color,
) {
    var isDark by mutableStateOf(is_dark)
    var homeBottomItemSelected by mutableStateOf(home_bottom_item_selected)
    var homeBottomItemUnSelected by mutableStateOf(home_bottom_item_un_selected)
    var uiBackground by mutableStateOf(ui_background)
    var homeBottomItemBg by mutableStateOf(home_bottom_item_bg)
    var homeIconText by mutableStateOf(home_icon_text)

    fun update(other: CarByComposeColors) {
        isDark = other.isDark
        homeBottomItemSelected = other.homeBottomItemSelected
        homeBottomItemUnSelected = other.homeBottomItemUnSelected
        uiBackground = other.uiBackground
        homeBottomItemBg = other.homeBottomItemBg
        homeIconText = other.homeIconText
    }

    fun copy(): CarByComposeColors = CarByComposeColors(
        is_dark = isDark,
        home_bottom_item_selected = homeBottomItemSelected,
        home_bottom_item_un_selected = homeBottomItemUnSelected,
        ui_background = uiBackground,
        home_bottom_item_bg = homeBottomItemBg,
        home_icon_text = homeIconText,
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

