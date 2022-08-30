package com.app.newsapplication.data.repository.remotedata

import android.util.Log
import com.app.newsapplication.data.model.NewsData
import com.app.newsapplication.data.model.NewsListDataResponse
import com.app.newsapplication.data.repository.NewsApi
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRemoteSource @Inject constructor(private val newsApi: NewsApi){

    private val TAG = NewsRemoteSource::class.java.simpleName
    private var cachedList: List<NewsData>? = null

    suspend fun getNewsList(): List<NewsData> = withContext(Dispatchers.IO) {
        var cachedCategories = cachedList
        if (cachedCategories == null) {
            Log.d(TAG, "getNewsList: " + Gson().toJson(newsApi.getNewsData().mapCategoriesToItems()))
            cachedCategories = newsApi.getNewsData().mapCategoriesToItems()
            this@NewsRemoteSource.cachedList = cachedCategories
        }
        return@withContext cachedCategories
    }

    private fun NewsListDataResponse.mapCategoriesToItems(): List<NewsData> {
        return this.newsList.map { newsData ->
            NewsData(
                id = newsData.id,
                title = newsData.title,
                content = newsData.content,
                date = newsData.date,
                time = newsData.time,
                author = newsData.author,
                imageUrl = newsData.imageUrl,
                readMoreUrl = newsData.readMoreUrl,
                url = newsData.url
            )
        }
    }

}