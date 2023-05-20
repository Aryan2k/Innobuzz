package com.example.innobuzz.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.innobuzz.db.entity.Posts

@Dao
interface PostsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPostList(postList: List<Posts>): LongArray

    @Query("SELECT * FROM Posts")
    suspend fun getPostList(): List<Posts>?

}