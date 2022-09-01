package com.app.newsapplication.di

import android.app.Application
import androidx.room.Room
import com.app.newsapplication.data.localdata.AppDatabase
import com.app.newsapplication.data.localdata.NewsDAO
import com.app.newsapplication.data.remotedata.NewsApi
import com.app.newsapplication.data.remotedata.NewsApi.Companion.API_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Provides
    @Singleton
    fun provideAuthInterceptorOkHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit
            .Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(API_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsApiService(
        retrofit: Retrofit
    ): NewsApi.Service {
        return retrofit.create(NewsApi.Service::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room
            .databaseBuilder(
                application,
                AppDatabase::class.java,
                "newsdatabase"
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(appDatabase: AppDatabase): NewsDAO {
        return appDatabase.newsDao()
    }
}