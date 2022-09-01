package com.app.newsapplication.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.newsapplication.data.localdata.NewsDAO
import com.app.newsapplication.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val remoteSource: NewsRepository,
    private val localSource: NewsDAO): ViewModel(){

        var state by mutableStateOf(
            NewsListStateManagment.State(
                newsList = listOf(),
                isLoading = true
            )
        )

    private set
    var effects = Channel<NewsListStateManagment.Effect>(UNLIMITED)
    private set

    init {
        viewModelScope.launch { getTotalNewsList() }
    }

    private suspend fun getTotalNewsList() {
        var newsData = localSource.getAllNews()
        if(newsData.isEmpty()){
             newsData = remoteSource.getNewsList()
        }
        viewModelScope.launch {
            state = state.copy(newsList = newsData, isLoading = false)
            effects.send(NewsListStateManagment.Effect.DataWasLoaded)
        }
    }
}