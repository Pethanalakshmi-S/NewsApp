package com.app.newsapplication.data.repository

import com.app.newsapplication.data.model.NewsListDataResponse
import retrofit2.http.GET
import javax.inject.Inject

class NewsApi @Inject constructor(private val service: Service) {
    suspend fun getNewsData(): NewsListDataResponse = service.getNewsList()

    interface Service{
        @GET("news?category=all")
        suspend fun getNewsList(): NewsListDataResponse
    }

    companion object {
        const val API_URL = "https://inshorts.deta.dev/"
    }
}