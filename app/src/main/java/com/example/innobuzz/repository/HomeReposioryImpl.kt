package com.example.innobuzz.repository

import com.example.innobuzz.db.PostsDatabase
import com.example.innobuzz.db.entity.Posts
import com.example.innobuzz.network.PostsApi
import com.example.innobuzz.utils.Resource
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private var database: PostsDatabase, private var apiService: PostsApi) : HomeRepository {

    override suspend fun getAllPostsFromApi(): Resource<List<Posts>?> {
        return try {
            val result = apiService.getAllPostsAsync().await()
            Resource.success(result)
        } catch (exception: java.lang.Exception) {
            Resource.error(null, exception.localizedMessage!!.toString())
        }
    }

    override suspend fun insertAllPosts(postsList: List<Posts>?): LongArray? {
        return postsList?.let { database.getPostsDao()?.insertPostList(it) }
    }

    override suspend fun getAllPostsFromDb(): List<Posts>? {
        return database.getPostsDao()?.getPostList()
    }
}
