package com.example.innobuzz.di

import android.content.Context
import androidx.room.Room.databaseBuilder
import com.example.innobuzz.db.PostsDatabase
import com.example.innobuzz.network.PostsApi
import com.example.innobuzz.repository.HomeRepository
import com.example.innobuzz.repository.HomeRepositoryImpl
import com.example.innobuzz.utils.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModules {

    @Singleton
    @Provides
    fun providePostsApi(): PostsApi {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .build()
            .create(PostsApi::class.java)
    }

    @Singleton
    @Provides
    fun providePostsDatabase(@ApplicationContext context: Context): PostsDatabase {
        return databaseBuilder(
            context,
            PostsDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository {
        return homeRepositoryImpl
    }
}