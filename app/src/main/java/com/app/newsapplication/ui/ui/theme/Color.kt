package com.app.newsapplication.ui.ui.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

enum class CustomTheme{
    DARK, LIGHT, DEFAULT
}

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val orange = Color(0xFFFF5722)
val pink = Color(0xFFE91E63)
val Purple = Color(0xFF3F51B5)
val violet = Color(0xFF673AB7)
val White = Color(0xFFCCCCCC)
val Black = Color(0xFF222222)

@Stable
class CustomColor(
    backgroundColor: Color,
    buttonBackgroundColor: Color,
    textColor: Color,
    buttonTextColor: Color
){
    var backgroundColor by mutableStateOf(backgroundColor)
    private set

    var buttonBackgroundColor by mutableStateOf(buttonBackgroundColor)
    private set

    var textColor by mutableStateOf(textColor)
    private set

    var buttonTextColor by mutableStateOf(buttonTextColor)
    private set

    fun update(colors: CustomColor){
        this.backgroundColor = colors.backgroundColor
        this.buttonBackgroundColor = colors.buttonBackgroundColor
        this.buttonTextColor = colors.buttonTextColor
        this.textColor = colors.textColor
    }

    fun copy() = CustomColor(
        backgroundColor,
        buttonBackgroundColor,
        textColor,
        buttonTextColor
    )
}