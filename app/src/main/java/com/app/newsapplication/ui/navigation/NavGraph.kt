package com.app.newsapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.newsapplication.data.model.NewsDataDetails
import com.app.newsapplication.ui.NewsListData
import com.app.newsapplication.ui.SearchListData
import com.app.newsapplication.ui.newsdetails.NewsDetailScreen
import com.app.newsapplication.ui.settings.SettingsScreen

@Composable
fun SetupNavGraph(navController: NavController) {
    val navController = rememberNavController()
    var url: String? = ""
    NavHost(
        navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            NewsListData(navController)
        }

        composable(route = BottomBarScreen.Settings.route){
            SettingsScreen(navController = navController)
        }

        composable(route = Screen.NewsDetail.route) {
            val result =
                navController.previousBackStackEntry?.savedStateHandle?.get<NewsDataDetails>("news")
            url = result?.url.toString()
            NewsDetailScreen(navController = navController, url.toString())
        }
        composable(route = Screen.Search.route){
            SearchListData(navController = navController)
        }
    }
}