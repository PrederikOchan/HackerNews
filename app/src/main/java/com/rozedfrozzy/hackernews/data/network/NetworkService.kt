package com.rozedfrozzy.hackernews.data.network

import com.rozedfrozzy.hackernews.data.db.entity.Story
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class NetworkService {
    private var mRetrofit: Retrofit? = null

    fun fetchTopNews(): Single<List<Long>>? {
        return getRetrofit()?.create(NetworkService.ApiServices::class.java)?.getTopNews()
    }

    fun fetchNewsById(newsId: String): Single<Story>? {
        return getRetrofit()?.create(NetworkService.ApiServices::class.java)?.getNewsById(newsId)
    }

    private fun getRetrofit(): Retrofit? {
        if (mRetrofit == null) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
            mRetrofit = Retrofit
                .Builder()
                .baseUrl("https://hacker-news.firebaseio.com/v0/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
        }
        return mRetrofit
    }

    interface ApiServices {
        @GET("topstories.json")
        fun getTopNews(): Single<List<Long>>

        @GET("item/{itemId}.json")
        fun getNewsById(
            @Path("itemId") itemId: String
        ): Single<Story>
    }
}