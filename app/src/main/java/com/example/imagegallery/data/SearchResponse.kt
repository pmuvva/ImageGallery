package com.example.imagegallery.data

data class SearchResponse(val title: String,
            val link: String,
            val description: String,
            val modified: String,
            val generator: String,
            val items: List<ImageDetails>)
