package com.app.newsapplication.ui.navigation

sealed class Screen(val route: String) {
    //object Home: Screen(" Home ")
    object NewsDetail: Screen("NewsDetail")
    object Search: Screen("Search")
}