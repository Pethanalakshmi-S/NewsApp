package com.app.newsapplication.ui.settings

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.newsapplication.R


@Composable
fun SettingsScreen(
    navController: NavController,
) {

    Scaffold(
        topBar = {
            ToolBar(navController)
        },
    ) {
        Box {
            MainScreen()
        }
    }
}

@Composable
private fun ToolBar(navController: NavController) {
    val textState = remember { mutableStateOf("") }
    TopAppBar(
        title = {
            Row() {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    modifier = Modifier.padding(5.dp),
                    contentDescription = null,
                    tint = Color.White
                )
                Text(text = "Account Settings",
                    modifier = Modifier.align(alignment = Alignment.CenterVertically).padding(5.dp))
            }

        })
}


/*@Composable
fun Screen(
    items: List<Item>,
) {
    // Here we store each state of expandable item
    val expandStates = remember(items.size) {
        List(items.size) { mutableStateOf(false) }
    }

    LazyColumn {
        itemsIndexed(items, { _, it -> it.key }) { idx, item ->
            ExpandableItem(
                modifier = Modifier.fillMaxWidth(),
                item = item.expandable,
                isExpanded = expandStates[idx].value,
            )
        }
    }
}*/

@Composable
fun MainScreen() {
    val list = getMainList()
    val context = LocalContext.current
    val expandStates = remember(list.size) {
        List(list.size) { mutableStateOf(false) }
    }

    LazyColumn {
        items(list) {
            ListItem(mainClass = it){
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
    }


}

@Composable
fun ListItem(mainClass: MainClass,call:(String)->Unit) {
    val mRemember = remember { mutableStateOf(false) }

    Text(
        text = mainClass.name,
        style = TextStyle(color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal),
        modifier = Modifier.padding(10.dp)
    )
    Column( modifier = Modifier.padding(8.dp)) {
        mainClass.list.forEach {
            Row( modifier = Modifier.fillMaxWidth().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    modifier= Modifier
                        .clickable { call.invoke(it) },
                    text = it,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
                if(it == "Applock"){
                    CheckedSwitch("applock")
                }
                if(it == "Follow device theme"){
                    CheckedSwitch("device theme")
                }
            }

            if(it == "Choose Theme"){
                Row(horizontalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)) {
                    Image(painter = painterResource(id = R.drawable.round_brightness_1_black_24),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(
                            Color(0xFF3F51B5),
                            BlendMode.SrcIn
                        ),
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(16.dp))
                            .height(24.dp))

                    Image(painter = painterResource(id = R.drawable.round_brightness_1_black_24),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(
                            Color(0xFFE91E63),
                            BlendMode.SrcIn
                        ),
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(16.dp))
                            .height(24.dp))

                    Image(painter = painterResource(id = R.drawable.round_brightness_1_black_24),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(
                            Color(0xFF673AB7),
                            BlendMode.SrcIn
                        ),
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(16.dp))
                            .height(24.dp))

                    Image(painter = painterResource(id = R.drawable.round_brightness_1_black_24),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(
                            Color(0xFF03DAC5),
                            BlendMode.SrcIn
                        ),
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(16.dp))
                            .height(24.dp))

                    Image(painter = painterResource(id = R.drawable.round_brightness_1_black_24),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(
                            Color(0xFFFF5722),
                            BlendMode.SrcIn
                        ),
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(16.dp))
                            .height(24.dp))

                    Image(painter = painterResource(id = R.drawable.round_brightness_1_black_24),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(
                            Color(0xFF3700B3),
                            BlendMode.SrcIn
                        ),
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(16.dp))
                            .height(24.dp))
                }
            }

        }
    }

}

@Composable
fun CheckedSwitch(s: String) {
    val mRemember = remember { mutableStateOf(false) }

    Switch(
        checked = mRemember.value,
        onCheckedChange = { mRemember.value = it },
        enabled = true,
        colors = SwitchDefaults.colors(
            checkedThumbColor = Color.Cyan
        ),
        modifier = Modifier.padding(0.dp)
    )
}


data class MainClass(val name: String, val list: List<String>)

fun getMainList(): List<MainClass> {
    val list = mutableListOf<MainClass>()
    list.add(MainClass(name = "Privacy & Security",
        list = mutableListOf("Applock", "Analytics", "Privacy policy","Terms of use")))
    list.add(MainClass(name = "About",
        list = mutableListOf("About us","Rate app","share","Clear cache and Reset app")))
    list.add(MainClass(name = "Display setings",
        list = mutableListOf("Widget", "Fonts", "Language")))
    list.add(MainClass(name = "Themes",
        list = mutableListOf("Choose Theme", "Follow device theme", "Dark theme")))
    list.add(MainClass(name = "Help center",
        list = mutableListOf("Resources and FAQ")))
    return list
}
