package com.app.newsapplication.data.localdata

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNewsData(newsData: List<NewsData>)

    @Query("SELECT * FROM newsdata")
    suspend fun getAllNews(): List<NewsData>

    @Query("SELECT * FROM newsdata WHERE title")
    suspend fun searchNews(): List<NewsData>

    @Query("SELECT * FROM newsdata WHERE title" +
            " LIKE:startsWith || '%' ORDER BY id DESC")
    suspend fun searchNews(startsWith:String):List<NewsData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(employee:NewsData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list:List<NewsData>)

    @Query("DELETE FROM newsdata")
    suspend fun clear()
}