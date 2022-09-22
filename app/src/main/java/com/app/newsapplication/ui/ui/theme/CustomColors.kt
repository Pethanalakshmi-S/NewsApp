package com.app.newsapplication.ui.ui.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

@Stable
class CustomColors(
    primary: Color,
    primaryVariant: Color,
    primaryInteractive: Color,
    primarySelected: Color,
    onPrimary: Color,
    secondary: Color,
    secondaryVariant: Color,
    onSecondary: Color,
    tertiary: Color,
    tertiary_01_100: Color,
    tertiary_01_300: Color,
    tertiary_01_500: Color,
    tertiary_01_700: Color,
    tertiary_02_100: Color,
    tertiary_02_300: Color,
    tertiary_02_500: Color,
    isLight: Boolean,
    // Rest of the colors here

) {
    var primary by mutableStateOf(primary)
        private set
    var primaryVariant by mutableStateOf(primaryVariant)
        private set
    var primaryInteractive by mutableStateOf(primaryInteractive)
        private set
    var primarySelected by mutableStateOf(primarySelected)

    fun update(other: CustomColors) {
        primary = other.primary
        primaryVariant = other.primaryVariant
        primaryInteractive = other.primaryInteractive
        primarySelected = other.primarySelected
    }
}