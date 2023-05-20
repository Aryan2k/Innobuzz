package com.example.innobuzz.network

import com.example.innobuzz.db.entity.Posts
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface PostsApi {

    @GET("posts")
    fun getAllPostsAsync(): Deferred<ArrayList<Posts>>
}