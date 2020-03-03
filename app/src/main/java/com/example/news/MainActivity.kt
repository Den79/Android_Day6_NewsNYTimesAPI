package com.example.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.news.network.Article
import com.example.news.network.ArticleResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class MainActivity : AppCompatActivity() {

    // Moshi to help parse our JSON response into kotlin class objects
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    // Retrofit as our HTTP Client to interact with the Api
    private val retrofit: Retrofit? = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl("https://api.nytimes.com/svc/")
        .build()

    // Retrofit requires us to add an interface (see below)
    // within which we add method declarations to outline
    // the endpoints that we want to access
    private var api: API = retrofit!!.create<API>(API::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //println(BuildConfig.API_KEY)

        // We will use a coroutine to help us simplify the asynchronous code
        getPopularArticles()
    }

    // This is our coroutine (by adding GlobalScope launch Main)
    private fun getPopularArticles() = GlobalScope.launch(Dispatchers.Main){
        // This implements the interface asynchronously
        // without blocking the main thread and potentially freezing our app
        val firstResult: Article = api.getPopularArticlesAsync(BuildConfig.API_KEY).results.first()

        // Upon completion of the HTTP request we update the layout element
        introText.text = firstResult.title
    }
}

// Retrofit requires us to use an interface, this is where we define our endpoints
interface API {
    @GET("mostpopular/v2/viewed/1.json")
    suspend fun getPopularArticlesAsync(@Query("api-key") apiKey: String): ArticleResponse

    // Example
    //@GET("mostpopular/v2/emailed/1.json")
    //suspend fun getEmailedArticlesAsync(@Query("api-key") apiKey: String): ArticleResponse
}
