package com.example.innobuzz.repository

import com.example.innobuzz.db.entity.Posts
import com.example.innobuzz.utils.Resource

interface HomeRepository {

    suspend fun getAllPostsFromApi(): Resource<List<Posts>?>
    suspend fun insertAllPosts(postsList: List<Posts>?): LongArray?
    suspend fun getAllPostsFromDb(): List<Posts>?

}