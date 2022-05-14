package com.example.imagegallery.remote

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.imagegallery.SearchViewModel

class SearchViewModelFactory(private val context: Context, private val tag: String) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(context, tag) as T
    }
}