package com.app.newsapplication.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.app.newsapplication.ui.home.NewsListScreen
import com.app.newsapplication.ui.home.NewsListViewModel
import com.app.newsapplication.ui.navigation.SetupNavGraph
import com.app.newsapplication.ui.search.SearchScreen
import com.app.newsapplication.ui.search.SearchViewModel
import com.app.newsapplication.ui.ui.theme.AppTheme
import com.app.newsapplication.ui.ui.theme.backgroundColor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.receiveAsFlow
import androidx.datastore.preferences.preferencesDataStore
import com.google.accompanist.systemuicontroller.rememberSystemUiController

val Context.dataStore by preferencesDataStore("settings")

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(color = backgroundColor()) {
                AppTheme(){
                    val navController = rememberNavController()
                    SetupNavGraph(navController = navController)
                }
            }
        }
    }
}


@Composable
fun NewsListData(navController: NavHostController) {
    val viewModel: NewsListViewModel = hiltViewModel()
    NewsListScreen(
        state = viewModel.state,
        effectFlow = viewModel.effects.receiveAsFlow(),
        navController
    )
}

@Composable
fun SearchListData(navController: NavHostController) {
    val searchViewModel: SearchViewModel = hiltViewModel()
    SearchScreen(
        state = searchViewModel.state,
        effectFlow = searchViewModel.effects.receiveAsFlow(),
        navController = navController,
        viewModel = searchViewModel
    )
}
