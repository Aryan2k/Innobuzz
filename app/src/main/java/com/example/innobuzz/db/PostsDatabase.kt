package com.example.innobuzz.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.innobuzz.db.dao.PostsDao
import com.example.innobuzz.db.entity.Posts

@Database(entities = [Posts::class], exportSchema = false, version = 1)
abstract class PostsDatabase : RoomDatabase() {
    abstract fun getPostsDao(): PostsDao?
}
