package com.app.newsapplication.ui.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import com.app.newsapplication.ui.dataStore
import com.app.newsapplication.ui.settings.ThemeViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun AppTheme(
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val viewModel = remember { ThemeViewModel(context.dataStore) }
    val state = viewModel.state.observeAsState()
    val value = state.value ?: isSystemInDarkTheme()
    val systemUiController = rememberSystemUiController()

    LaunchedEffect(viewModel) { viewModel.request() }

    if(value) systemUiController.setStatusBarColor(DBlue)
    else systemUiController.setStatusBarColor(SilverLight)

    DarkThemeValue.current.value = value
    MaterialTheme(
        colors = if (value) AppDarkColors else AppLightColors,
        typography = AppTypography,
        shapes = Shapes,
        content = content
    )
}

@Composable
@ReadOnlyComposable
fun isDarkTheme() = DarkThemeValue.current.value

@SuppressLint("CompositionLocalNaming")
val DarkThemeValue = compositionLocalOf { mutableStateOf(false) }

@Composable
@ReadOnlyComposable
infix fun <T> T.orInLightTheme(other: T): T = if (isDarkTheme()) this else other
