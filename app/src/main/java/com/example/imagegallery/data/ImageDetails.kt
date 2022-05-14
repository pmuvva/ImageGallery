package com.example.imagegallery.data

import java.io.Serializable

data class ImageDetails (val title: String,
                 val link: String,
                 val media: Media,
                 val date_taken: String,
                 val description: String,
                 val published: String,
                 val author: String,
                 val author_id: String,
                 val tags: String): Serializable