package com.app.newsapplication.ui.settings

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.newsapplication.R
import com.app.newsapplication.ui.dataStore
import com.app.newsapplication.ui.navigation.BottomBarScreen
import com.app.newsapplication.ui.ui.theme.backgroundColor
import com.app.newsapplication.ui.ui.theme.captionColor


@Composable
fun SettingsScreen(
    navController: NavController
) {
    val context = LocalContext.current

    val viewModel = remember {
        ThemeViewModel(context.dataStore)
    }
    val value = viewModel.state.observeAsState().value
    val systemInDarkTheme = isSystemInDarkTheme()

    val darkModeChecked by remember(value) {
        val checked = when (value) {
            null -> systemInDarkTheme
            else -> value
        }
        mutableStateOf(checked)
    }
    val useDeviceModeChecked by remember(value) {
        val checked = when (value) {
            null -> true
            else -> false
        }
        mutableStateOf(checked)
    }

    LaunchedEffect(viewModel) {
        viewModel.request()
    }

    Surface(color = backgroundColor()) {
        Scaffold(
            topBar = {
                ToolBar(navController)
            },
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                MainScreen(context, useDeviceModeChecked, darkModeChecked, viewModel)
            }
        }
    }
}

@Composable
private fun ToolBar(navController: NavController) {
    remember { mutableStateOf("") }
    TopAppBar(
        backgroundColor = backgroundColor(),
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                modifier = Modifier
                    .padding(5.dp)
                    .clickable {
                        navController.navigate(BottomBarScreen.Home.route)
                    },
                contentDescription = null,
                tint = captionColor()
            )
        },
        title = {
            Row() {
                Text(
                    text = stringResource(id = R.string.account_setting),
                    style = TextStyle(
                        color = captionColor(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier.padding(4.dp)
                )
            }
        })
}

@Composable
fun MainScreen(
    context: Context,
    useDeviceModeChecked: Boolean,
    darkModeChecked: Boolean,
    viewModel: ThemeViewModel
) {
    val list = getMainList()
    val resources = LocalContext.current.resources

    LazyColumn(Modifier.background(color = backgroundColor())) {
        items(list) { it ->
            ListItem(useDeviceModeChecked, darkModeChecked, viewModel, mainClass = it) {
                Toast.makeText(
                    context,
                    resources.getString(it), Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}

@Composable
fun ListItem(
    useDeviceModeChecked: Boolean,
    darkModeChecked: Boolean,
    viewModel: ThemeViewModel,
    mainClass: MainClass, call: (Int) -> Unit) {

    Text(
        text = stringResource(id = mainClass.name),
        style = TextStyle(
            color = captionColor(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal
        ),
        modifier = Modifier.padding(10.dp)
    )
    Column(modifier = Modifier.padding(8.dp)) {
        mainClass.list.forEach {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .clickable {
                            call.invoke(it.first)
                            if (it.first == R.string.dark_theme)
                                viewModel.switchToUseDarkMode(true)
                        },
                    text = stringResource(id = it.first),
                    style = TextStyle(
                        color = captionColor(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
                if (it.second == 1) {
                    CheckedSwitch(it, viewModel, useDeviceModeChecked)
                }
            }

            if (it.second == 2) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.round_brightness_1_black_24),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(
                            color = colorResource(id = R.color.purple),
                            blendMode = BlendMode.SrcIn
                        ),
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(16.dp))
                            .height(24.dp)
                    )

                    Image(
                        painter = painterResource(id = R.drawable.round_brightness_1_black_24),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(
                            color = colorResource(id = R.color.pink),
                            blendMode = BlendMode.SrcIn
                        ),
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(16.dp))
                            .height(24.dp)
                    )

                    Image(
                        painter = painterResource(id = R.drawable.round_brightness_1_black_24),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(
                            color = colorResource(id = R.color.voilet),
                            BlendMode.SrcIn
                        ),
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(16.dp))
                            .height(24.dp)
                    )

                    Image(
                        painter = painterResource(id = R.drawable.round_brightness_1_black_24),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(
                            color = colorResource(id = R.color.teal_200),
                            blendMode = BlendMode.SrcIn
                        ),
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(16.dp))
                            .height(24.dp)
                    )

                    Image(
                        painter = painterResource(id = R.drawable.round_brightness_1_black_24),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(
                            color = colorResource(id = R.color.orange),
                            blendMode = BlendMode.SrcIn
                        ),
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(16.dp))
                            .height(24.dp)
                    )

                    Image(
                        painter = painterResource(id = R.drawable.round_brightness_1_black_24),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(
                            color = colorResource(id = R.color.purple_700),
                            blendMode = BlendMode.SrcIn
                        ),
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(16.dp))
                            .height(24.dp)
                    )
                }
            }

        }
    }

}


@Composable
fun CheckedSwitch(pair: Pair<Int, Int>, viewModel: ThemeViewModel, useDeviceModeChecked: Boolean) {
    val mRemember = remember { mutableStateOf(false) }

    Switch(
        checked = if(pair.first == R.string.follow_device_theme) useDeviceModeChecked else mRemember.value,
        onCheckedChange = { if(pair.first == R.string.follow_device_theme) viewModel.switchToUseSystemSettings(true)
            else mRemember.value = it },
        enabled = true,
        colors = SwitchDefaults.colors(
            checkedThumbColor = Color.Cyan
        ),
        modifier = Modifier
            .padding(0.dp)
            .clickable {
                if (pair.first == R.string.app_lock) {

                }
            }
    )
}


data class MainClass(@StringRes val name: Int, val list: List<Pair<Int, Int>>)

fun getMainList(): List<MainClass> {
    val list = mutableListOf<MainClass>()
    list.add(
        MainClass(
            R.string.privacy_security,
            list = mutableListOf(
                Pair(R.string.app_lock, 1),
                Pair(R.string.alaytics, 0),
                Pair(R.string.privacy_policy, 0),
                Pair(R.string.terms_of_use, 0)
            )
        )
    )
    list.add(
        MainClass(
            R.string.about,
            list = mutableListOf(
                Pair(R.string.about_us, 0),
                Pair(R.string.rate_app, 0),
                Pair(R.string.share, 0),
                Pair(R.string.clear_cache, 0)
            )
        )
    )
    list.add(
        MainClass(
            R.string.display_setting,
            list = mutableListOf(
                Pair(R.string.widget, 0),
                Pair(R.string.fonts, 0),
                Pair(R.string.language, 0)
            )
        )
    )
    list.add(
        MainClass(
            R.string.themes,
            list = mutableListOf(
                Pair(R.string.choose_themes, 2),
                Pair(R.string.follow_device_theme, 1),
                Pair(R.string.dark_theme, 0)
            )
        )
    )
    list.add(
        MainClass(
            R.string.help_center,
            list = mutableListOf(Pair(R.string.resource_faq, 0))
        )
    )
    return list
}
