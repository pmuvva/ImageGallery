package com.example.imagegallery

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imagegallery.data.SearchResponse
import com.example.imagegallery.repository.ImageRepository
import com.example.imagegallery.util.Utils.isInternetAvailable

class SearchViewModel(private val context: Context, private val tag: String) : ViewModel() {

    private var listData = MutableLiveData<SearchResponse>()

    fun init(tag: String){
        val imageRepository : ImageRepository by lazy {
            ImageRepository
        }
        if(context.isInternetAvailable()) {
            listData = imageRepository.getLiveData(context,tag)
        }
    }

    fun getData() : MutableLiveData<SearchResponse>{
        return listData
    }
}