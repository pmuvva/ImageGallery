package com.example.imagegallery.remote

import com.example.imagegallery.data.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("services/feeds/photos_public.gne?format=json&nojsoncallback=1")
    fun getImages(@Query("tags") tags: String): Call<SearchResponse>
}