package com.example.news.network

data class Article(
    val title: String
)

data class ArticleResponse(
    val results: List<Article>
)