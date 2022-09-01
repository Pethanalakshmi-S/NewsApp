package com.app.newsapplication.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.app.newsapplication.ui.home.NewsListScreen
import com.app.newsapplication.ui.home.NewsListViewModel
import com.app.newsapplication.ui.navigation.Screen
import com.app.newsapplication.ui.navigation.SetupNavGraph
import com.app.newsapplication.ui.search.SearchScreen
import com.app.newsapplication.ui.search.SearchViewModel
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

@Composable
fun SearchListData(navController: NavHostController){
    val searchViewModel: SearchViewModel = hiltViewModel()
    SearchScreen(
        state = searchViewModel.state,
        effectFlow = searchViewModel.effects.receiveAsFlow(),
        navController = navController,
        viewModel = searchViewModel
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NewsApplicationTheme {
       // NewsApp()
    }
}