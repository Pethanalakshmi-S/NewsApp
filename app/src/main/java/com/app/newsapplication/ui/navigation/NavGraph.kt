package com.app.newsapplication.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.newsapplication.data.model.NewsDataDetails
import com.app.newsapplication.ui.NewsListData
import com.app.newsapplication.ui.SearchListData
import com.app.newsapplication.ui.newsdetails.NewsDetailScreen

@Composable
fun SetupNavGraph(navController: NavController) {
    val navController = rememberNavController()
    var url: String? = ""
    NavHost(
        navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            NewsListData(navController)
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