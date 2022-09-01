package com.app.newsapplication.ui.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.newsapplication.data.localdata.NewsDAO
import com.app.newsapplication.data.localdata.NewsData
import com.app.newsapplication.ui.home.NewsListStateManagment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject
constructor(
    private val localSource: NewsDAO,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _searchedNews = MutableStateFlow(emptyList<NewsData>())
    var searchedNews: StateFlow<List<NewsData>> = _searchedNews

    var state by mutableStateOf(
        NewsListStateManagment.State(
            newsList = listOf(),
            isLoading = true
        )
    )

        private set
    var effects = Channel<NewsListStateManagment.Effect>(Channel.UNLIMITED)
        private set

    init {
        loadSearchedNews("")
    }

    private suspend fun getSearchNewsList(startsWith: String) {
        var newsData = localSource.searchNews(startsWith)
        viewModelScope.launch {
            state = state.copy(newsList = newsData, isLoading = false)
            effects.send(NewsListStateManagment.Effect.DataWasLoaded)
        }
    }

    fun loadSearchedNews(startsWith: String) =  effect {
        viewModelScope.launch { getSearchNewsList(startsWith) }
        Log.d("xapp", "Loading News")
    }

   /* fun insert(news: NewsData){
        viewModelScope.launch(Dispatchers.IO) {
            localSource.insert(news)
            loadSearchedEmployees("")
        }
    }

    fun clear(){
        viewModelScope.launch(Dispatchers.IO) {
            localSource.clear()
            loadSearchedEmployees("")
        }
    }*/

    private fun effect(block: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) { block() }
    }
}