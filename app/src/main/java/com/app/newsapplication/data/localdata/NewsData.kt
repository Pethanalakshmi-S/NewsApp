package com.app.newsapplication.data.localdata

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "newsdata")
data class NewsData(
    @PrimaryKey(autoGenerate = true)
    var newsId:Int,
    var id: String?="",
    var content: String?="",
    var title: String?="",
    var date: String?="",
    var time: String?="",
    var author: String?="",
    var readMoreUrl: String?="",
    var imageUrl: String?="",
    var url: String?="",
)