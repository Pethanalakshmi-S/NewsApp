package com.app.newsapplication.ui.search

import com.app.newsapplication.data.localdata.NewsData

class SearchStateManagement {
    data class State(
        val newsList: List<NewsData> = listOf(),
        val isLoading:Boolean = false
    )

    sealed class Effect {
        object DataWasLoaded : Effect()
    }
}