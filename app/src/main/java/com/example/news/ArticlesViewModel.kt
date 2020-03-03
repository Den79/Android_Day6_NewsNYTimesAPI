package com.example.news

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.news.network.Article
import com.example.news.network.NewsAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ArticlesViewModel(): ViewModel(), LifecycleObserver {
    private val api = NewsAPI()
    val articles = MutableLiveData<List<Article>>()

    fun loadPopularArticles() = GlobalScope.launch(Dispatchers.Main) {
        val popularArticles = api.getPopularArticles()

        if (popularArticles != null) {
            articles.value = popularArticles
        }
    }
}