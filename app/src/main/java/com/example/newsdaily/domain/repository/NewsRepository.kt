package com.example.newsdaily.domain.repository

import com.example.newsdaily.data.remote.model.NewsResponse
import com.example.newsdaily.network.Result

interface NewsRepository {
    suspend fun getTopHeadlines(): Result<NewsResponse>
}
