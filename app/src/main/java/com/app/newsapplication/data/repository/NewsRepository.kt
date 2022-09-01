package com.app.newsapplication.data.repository

import android.util.Log
import com.app.newsapplication.data.localdata.NewsData
import com.app.newsapplication.data.model.NewsListDataResponse
import com.app.newsapplication.data.localdata.NewsDAO
import com.app.newsapplication.data.remotedata.NewsApi
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val newsApi: NewsApi,
    private val newsDAO: NewsDAO){

    private val TAG = NewsRepository::class.java.simpleName
    private var cachedList: List<NewsData>? = null

    suspend fun getNewsDatabase():List<NewsData> = withContext(Dispatchers.IO){
        cachedList =  newsDAO.getAllNews()
        return@withContext cachedList!!
    }

    suspend fun getNewsList(): List<NewsData> = withContext(Dispatchers.IO) {
        var cachedCategories = getNewsDatabase()
        if (cachedCategories.isEmpty()) {
            Log.d(TAG, "getNewsList: " + Gson().toJson(newsApi.getNewsData().mapCategoriesToItems()))
            cachedCategories = newsApi.getNewsData().mapCategoriesToItems()
            newsDAO.insertNewsData(cachedCategories)
            this@NewsRepository.cachedList = cachedCategories
        }
        return@withContext cachedCategories
    }

    private fun NewsListDataResponse.mapCategoriesToItems(): List<NewsData> {
        return this.newsList.map { newsData ->
            NewsData(
                newsId = 0,
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