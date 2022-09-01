package com.app.newsapplication.ui.home

import com.app.newsapplication.data.localdata.NewsData

class NewsListStateManagment {
    data class State(
        val newsList: List<NewsData> = listOf(),
        val isLoading:Boolean = false
    )

    sealed class Effect {
        object DataWasLoaded : Effect()
    }
}