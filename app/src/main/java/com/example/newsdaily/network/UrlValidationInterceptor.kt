package com.example.newsdaily.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class UrlValidationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url

        if (url.toString().isBlank()) {
            throw IOException("Request URL is blank.")
        }

        val scheme = url.scheme
        if (scheme != "http" && scheme != "https") {
            throw IOException("Unsupported URL scheme: $scheme. Only http and https are allowed.")
        }

        return chain.proceed(request)
    }
}
