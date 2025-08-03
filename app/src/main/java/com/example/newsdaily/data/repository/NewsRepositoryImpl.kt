package com.example.newsdaily.data.repository

import com.example.newsdaily.BuildConfig
import com.example.newsdaily.data.remote.api.NewsApiService
import com.example.newsdaily.data.remote.model.NewsResponse
import com.example.newsdaily.domain.repository.NewsRepository
import com.example.newsdaily.network.RemoteSource
import com.example.newsdaily.network.Result
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class NewsRepositoryImpl @Inject constructor(
    private val apiService: NewsApiService,
    private val dispatcher: CoroutineDispatcher
) : NewsRepository {

    override suspend fun getTopHeadlines(): Result<NewsResponse> = withContext(dispatcher) {
        RemoteSource.safeApiCall {
            apiService.getTopHeadlines(apiKey = BuildConfig.NEWS_API_KEY)
        }
    }
}