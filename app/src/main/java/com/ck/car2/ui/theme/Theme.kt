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
    primary = color_primary,
    transparent = color_transparent,
    ui_background = color_white,
    main_tab_un_select_color = color_grey666,

    /**
     * 首页home0
     */
    home_banner_select = color_white,
    home_banner_un_select = color_white80,
    home_bottom_item_bg = color_white,
    home_icon_text = color_grey333,
    home_tab_text = color_grey333,
    home_tab_desc_text_select = color_white,
    home_tab_desc_text_un_select = color_grey999,
    home_search_bar_text_color = color_grey999,
)

/*private val DarkColorPalette = CarByComposeColors(
    is_dark = true,
    primary = color_primary,
    ui_background = color_white,
    main_tab_un_select_color = color_grey666,

    home_bottom_item_bg = color_white,
    home_icon_text = color_grey333,
    home_tab_text = color_grey666,
)*/

@Composable
fun CarByComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit
) {
//    val colors = if (darkTheme) DarkColorPalette else LightColorPalette
    val colors = LightColorPalette
    val sysUiController = rememberSystemUiController()
    SideEffect {
//        sysUiController.setSystemBarsColor(
//            color = colors.uiBackground.copy(alpha = AlphaNearOpaque)
//        )
//        sysUiController.setStatusBarColor(
//            color = colors.uiBackground.copy(alpha = AlphaNearOpaque)
//        )
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
    primary: Color,
    transparent: Color,
    ui_background: Color,
    main_tab_un_select_color: Color,
    home_banner_select: Color,
    home_banner_un_select: Color,
    home_bottom_item_bg: Color,
    home_icon_text: Color,
    home_tab_text: Color,
    home_tab_desc_text_select: Color,
    home_tab_desc_text_un_select: Color,
    home_search_bar_text_color: Color,
) {
    var isDark by mutableStateOf(is_dark)
    var primary by mutableStateOf(primary)
    var transparent by mutableStateOf(transparent)
    var uiBackground by mutableStateOf(ui_background)
    var mainTabUnSelect by mutableStateOf(main_tab_un_select_color)

    var homeBannerSelect by mutableStateOf(home_banner_select)
    var homeBannerUnSelect by mutableStateOf(home_banner_un_select)
    var homeBottomItemBg by mutableStateOf(home_bottom_item_bg)
    var homeIconText by mutableStateOf(home_icon_text)
    var homeTabText by mutableStateOf(home_tab_text)
    var homeTabDescTextSelect by mutableStateOf(home_tab_desc_text_select)
    var homeTabDescTextUnSelect by mutableStateOf(home_tab_desc_text_un_select)
    var homeSearchBarTextColor by mutableStateOf(home_search_bar_text_color)

    fun update(other: CarByComposeColors) {
        isDark = other.isDark
        primary = other.primary
        primary = other.primary
        transparent = other.transparent
        mainTabUnSelect = other.mainTabUnSelect

        homeBannerSelect = other.homeBannerSelect
        homeBannerUnSelect = other.homeBannerUnSelect
        homeBottomItemBg = other.homeBottomItemBg
        homeIconText = other.homeIconText
        homeTabText = other.homeTabText
        homeTabDescTextSelect = other.homeTabDescTextSelect
        homeTabDescTextUnSelect = other.homeTabDescTextUnSelect
        homeSearchBarTextColor = other.homeSearchBarTextColor
    }

    fun copy(): CarByComposeColors = CarByComposeColors(
        is_dark = isDark,
        primary = primary,
        transparent = transparent,
        ui_background = uiBackground,
        main_tab_un_select_color = mainTabUnSelect,

        home_banner_select = homeBannerSelect,
        home_banner_un_select = homeBannerUnSelect,
        home_bottom_item_bg = homeBottomItemBg,
        home_icon_text = homeIconText,
        home_tab_text = homeTabText,
        home_tab_desc_text_select = homeTabDescTextSelect,
        home_tab_desc_text_un_select = homeTabDescTextUnSelect,
        home_search_bar_text_color = homeSearchBarTextColor,
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

