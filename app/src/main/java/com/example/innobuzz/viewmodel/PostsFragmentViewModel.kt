package com.example.innobuzz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.innobuzz.db.entity.Posts
import com.example.innobuzz.repository.HomeRepository
import com.example.innobuzz.utils.RequestStatus
import com.example.innobuzz.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsFragmentViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    var getPostsFromDbLiveData = MutableLiveData<Resource<Nothing?>>()
    fun getPostsFromDb() {
        getPostsFromDbLiveData.postValue(Resource.loading(null))
        viewModelScope.launch {
            val result = repository.getAllPostsFromDb()
            if (result?.isNotEmpty() == true) {
                postsList.clear()
                postsList.addAll(result)
            }
            getPostsFromDbLiveData.postValue(Resource.success(null))
        }
    }

    var getPostsFromApiLiveData = MutableLiveData<Resource<List<Posts>?>>()
    var postsList = ArrayList<Posts>()
    fun getPostsFromApi() {
        getPostsFromApiLiveData.postValue(Resource.loading(null))
        viewModelScope.launch {
            val result = repository.getAllPostsFromApi()
            if (result.status == RequestStatus.SUCCESS) {
                val dbResult = repository.insertAllPosts(result.data)
                if (dbResult?.size == result.data?.size) {
                    val data = repository.getAllPostsFromDb()
                    data?.let {
                        postsList.clear()
                        postsList.addAll(it)
                    }
                    getPostsFromApiLiveData.postValue(Resource.success(null))
                } else {
                    getPostsFromApiLiveData.postValue(Resource.error(null, "Database insertion error"))
                }
            } else {
                getPostsFromApiLiveData.postValue(Resource.error(null, result.message.toString()))
            }
        }
    }

    var insertPostsToDbLiveData = MutableLiveData<Resource<LongArray>>()
    fun insertPostsToDb(postsList: List<Posts>) {
        insertPostsToDbLiveData.postValue(Resource.loading(null))
        viewModelScope.launch {
            val result = repository.insertAllPosts(postsList)
            insertPostsToDbLiveData.postValue(Resource.success(result))
        }
    }


}