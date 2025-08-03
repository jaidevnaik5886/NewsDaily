package com.example.newsdaily.data.remote.model

import com.example.newsdaily.domain.model.Article

data class NewsResponse(
    val status: String?,
    val totalResults: Int?,
    val articles: List<Article>?
)

