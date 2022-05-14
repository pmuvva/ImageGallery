package com.example.imagegallery.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.imagegallery.remote.ApiClient
import com.example.imagegallery.data.SearchResponse
import com.example.imagegallery.util.Utils.hideProgressBar
import com.example.imagegallery.util.Utils.showProgressBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ImageRepository {
    fun getLiveData(context: Context, tag: String) : MutableLiveData<SearchResponse> {

        val mutableLiveData = MutableLiveData<SearchResponse>()

        context.showProgressBar()

        ApiClient.apiService.getImages(tag).enqueue(object : Callback<SearchResponse> {
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                hideProgressBar()
                Log.e("error", t.localizedMessage)
            }

            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                hideProgressBar()
                val usersResponse = response.body()
                usersResponse?.let { mutableLiveData.value = it as SearchResponse }
            }

        })

        return mutableLiveData
    }
}