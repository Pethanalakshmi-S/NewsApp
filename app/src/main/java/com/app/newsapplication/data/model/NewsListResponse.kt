package com.app.newsapplication.data.model

import com.google.gson.annotations.SerializedName

data class NewsListDataResponse( @SerializedName("data") val newsList: List<NewsListResponse>)

data class NewsListResponse(
    @SerializedName("id") val id: String? = " ",
    @SerializedName("content") val content: String? = " ",
    @SerializedName("title") val title: String? = " ",
    @SerializedName("date") val date: String? = " ",
    @SerializedName("time") val time: String? = " ",
    @SerializedName("author") val author: String? = " ",
    @SerializedName("readMoreUrl") val readMoreUrl: String? = " ",
    @SerializedName("imageUrl") val imageUrl: String? = " ",
    @SerializedName("url") val url: String? = " ",
)