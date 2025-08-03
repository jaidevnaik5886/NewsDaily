package com.example.newsdaily.data.remote.api

import com.example.newsdaily.data.remote.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApiService {
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("category") category: String = "sports",
        @Query("apiKey") apiKey: String
    ): Response<NewsResponse>
}