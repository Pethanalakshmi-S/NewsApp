package com.app.newsapplication.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.newsapplication.ui.NavigationKeys.Arg.NEWS_ID
import com.app.newsapplication.ui.home.NewsListScreen
import com.app.newsapplication.ui.home.NewsListViewModel
import com.app.newsapplication.ui.navigation.Screen
import com.app.newsapplication.ui.navigation.SetupNavGraph
import com.app.newsapplication.ui.ui.theme.NewsApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.receiveAsFlow

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsApplicationTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                SetupNavGraph(navController = navController)
                //NewsApp()
            }
        }
    }
}

/*
@Composable
fun NewsApp() {
   val navController = rememberNavController()
    NavHost(navController, startDestination = NavigationKeys.Route.NEWS_LIST){
        composable(route = NavigationKeys.Route.NEWS_LIST){
            NewsListData(navController)
        }
    }
}
*/

@Composable
fun NewsListData(navController: NavHostController){
    val viewModel: NewsListViewModel = hiltViewModel()
    NewsListScreen(
        state = viewModel.state,
        effectFlow = viewModel.effects.receiveAsFlow(),
        onNavigationRequested = { itemId ->
            navController.navigate("${Screen.Home.route}/${itemId}")
        },
        navController)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NewsApplicationTheme {
       // NewsApp()
    }
}

object NavigationKeys{
    object Arg {
        const val NEWS_ID = "newsname"
    }

    object Route {
        const val NEWS_LIST = "news_list"
        const val NEWS_DETAILS = "$NEWS_LIST/{$NEWS_ID}"
    }
}