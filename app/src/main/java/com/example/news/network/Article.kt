package com.example.news.network

import com.squareup.moshi.Json

data class Article(
    val title: String,
    val media: List<Media>
)

data class ArticleResponse(
    val results: List<Article>
)

data class Media(
    val type: String,
    // media-metadata is not a very kotlin-ey name,
    // so we can change it by add the @Json annotation
    //  where name is the json key that we want to map
    @Json(name = "media-metadata") val mediaMetadata: List<MediaMetadata>
)

data class MediaMetadata(
    val url: String
)