package com.ck.car2.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

/**
 * 直接使用color的方式，不能换肤或者切换深浅色模式，所以要使用theme；
 * 直接使用Material Design的槽色，很可能不能满足实际项目需要，所以需要自定义主题配色；
 * 自定义主题也不需要把每个控件的颜色放上来，只是定义了一组app里面用的颜色，并不是毫无UI设计规则的；
 */
private val LightColorPalette = CarByComposeColors(
    is_dark = false,
    primary = color_primary,
    ui_background = color_white,
    transparent_white = color_white80,
    first_text_color = color_grey333,
    second_text_color = color_grey666,
    third_text_color = color_grey999,


    home_category_select = color_white,
    home_category_un_select = color_light_grey,
    color_home_category_title_un_select = color_home_category_title_un_select,
    color_home_category_left_un_select = color_home_category_left_un_select,
)

/* 暂时不处理深色模式
private val DarkColorPalette = CarByComposeColors(
    is_dark = true,
    primary = color_primary,
    ui_background = color_white,
)*/

@Composable
fun ComposeDemoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit
) {
//    val colors = if (darkTheme) DarkColorPalette else LightColorPalette
    val colors = LightColorPalette

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
    ui_background: Color,
    transparent_white: Color,
    first_text_color: Color,
    second_text_color: Color,
    third_text_color: Color,
    home_category_select: Color,
    home_category_un_select: Color,
    color_home_category_title_un_select: Color,
    color_home_category_left_un_select: Color,
) {
    var isDark by mutableStateOf(is_dark)
    var primary by mutableStateOf(primary)
    var uiBackground by mutableStateOf(ui_background)
    var transparentWhite by mutableStateOf(transparent_white)
    var firstTextColor by mutableStateOf(first_text_color)
    var secondTextColor by mutableStateOf(second_text_color)
    var thirdTextColor by mutableStateOf(third_text_color)

    var homeCategorySelect by mutableStateOf(home_category_select)
    var homeCategoryUnSelect by mutableStateOf(home_category_un_select)
    var homeCategoryTitleUnSelect by mutableStateOf(color_home_category_title_un_select)
    var homeCategoryLeftUnSelect by mutableStateOf(color_home_category_left_un_select)

    fun update(other: CarByComposeColors) {
        isDark = other.isDark
        primary = other.primary
        primary = other.primary
        firstTextColor = other.firstTextColor
        secondTextColor = other.secondTextColor
        homeCategorySelect = other.homeCategorySelect
        homeCategoryUnSelect = other.homeCategoryUnSelect
        homeCategoryTitleUnSelect = other.homeCategoryTitleUnSelect
        homeCategoryLeftUnSelect = other.homeCategoryLeftUnSelect
    }

    fun copy(): CarByComposeColors = CarByComposeColors(
        is_dark = isDark,
        primary = primary,
        ui_background = uiBackground,
        transparent_white = transparentWhite,
        first_text_color = firstTextColor,
        second_text_color = secondTextColor,
        third_text_color = thirdTextColor,

        home_category_select = homeCategorySelect,
        home_category_un_select = homeCategoryUnSelect,
        color_home_category_title_un_select = homeCategoryTitleUnSelect,
        color_home_category_left_un_select = homeCategoryLeftUnSelect,
    )
}


@Composable
fun ProvideCarByComposeColors(
    colors: CarByComposeColors, content: @Composable () -> Unit
) {
    val colorPalette = remember {
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

