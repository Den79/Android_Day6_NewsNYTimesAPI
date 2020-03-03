package com.example.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.news.network.Article
import com.example.news.network.NewsAPI
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private val api = NewsAPI()
    private var articles: List<Article> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // We will use a coroutine to help us simplify the asynchronous code
        loadPopularArticles()
    }

    private fun loadPopularArticles() = GlobalScope.launch(Dispatchers.Main) {
        val popularArticles = api.getPopularArticles()
        if(popularArticles != null) {
            articles = popularArticles
            introText.text = articles.first().title
        }
    }

}
