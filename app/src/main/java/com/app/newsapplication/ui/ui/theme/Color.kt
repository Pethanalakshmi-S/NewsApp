package com.app.newsapplication.ui.ui.theme

import android.annotation.SuppressLint
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

val Red = Color.fromRGB("#FF7C74")
val Blue = Color.fromRGB("#3F51B5")
val DarkBlue = Color.fromRGB("#0B1729")
val Silver20 = Color.fromRGB("#E5E5E5")
val SilverLight = Color.fromRGB("#F8F7F8")
val White = Color.fromRGB("#FFFFFF")
val DBlue = Color.fromRGB("#1F2B3B")

@Composable
fun backgroundColor() = DarkBlue orInLightTheme SilverLight

@Composable
fun captionColor() = Silver20 orInLightTheme DarkBlue

private fun Color.Companion.fromRGB(rgb: String) = Color(android.graphics.Color.parseColor(rgb))

@SuppressLint("ConflictingOnColor")
val AppLightColors = lightColors(
    primary = Blue,
    secondary = DarkBlue,
    background = White,
    surface = White,
    onPrimary = White,
    onBackground = DarkBlue,
    onSecondary = White,
)

@SuppressLint("ConflictingOnColor")
val AppDarkColors = darkColors(
    primary = Blue,
    secondary = White,
    background = DarkBlue,
    surface = DarkBlue,
    onPrimary = DarkBlue,
    onBackground = Silver20,
    onSecondary = DarkBlue,
)