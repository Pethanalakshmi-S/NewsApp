package com.app.newsapplication.ui.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.*

private val CustomDarkColors = CustomColor(
    backgroundColor = Black,
    buttonBackgroundColor = Black,
    textColor = White,
    buttonTextColor = Black
)

private val CustomLightColors = CustomColor(
    backgroundColor = White,
    buttonTextColor = White,
    textColor = Black,
    buttonBackgroundColor = Purple500
)


private val LocalColorsProvider = staticCompositionLocalOf {
    CustomLightColors
}

@Composable
private fun CustomLocalProvider(
    colors:CustomColor,
    content: @Composable() () -> Unit
){
    val colorPalette = remember {
        colors.copy()
    }

    colorPalette.update(colors)
    CompositionLocalProvider(LocalColorsProvider provides colorPalette, content = content)
}

private val CustomTheme.colors: Pair<Colors, CustomColor>
    get() = when (this){
        CustomTheme.DARK -> darkColors() to CustomDarkColors
        CustomTheme.LIGHT -> lightColors() to CustomLightColors
        CustomTheme.DEFAULT -> TODO()
    }

object CustomThemeManager {
    val colors: CustomColor
        @Composable
        get() = LocalColorsProvider.current

    var customTheme by mutableStateOf(CustomTheme.LIGHT)
    fun isSystemInDarkTheme(): Boolean{
        return customTheme == CustomTheme.DARK
    }
}

@Composable
fun CustomComposeTheme(
    customTheme: CustomTheme = CustomThemeManager.customTheme,
    content: @Composable() () -> Unit
){
    val (colorPalette, lcColor) = customTheme.colors

    CustomLocalProvider(colors = lcColor) {
        MaterialTheme(
            colors = colorPalette,
            typography = Typography,
            shapes = Shapes,
            content = content
        )

    }
}

/*
@Composable
fun NewsApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}*/
