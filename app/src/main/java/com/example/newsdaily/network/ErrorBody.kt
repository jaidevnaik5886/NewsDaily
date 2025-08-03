package com.example.newsdaily.network

import androidx.annotation.Keep
import java.net.HttpURLConnection

@Keep
data class ErrorBody(val message: String, val code: Int = -1, val rawMessage: String)

const val DEFAULT_ERROR_MESSAGE = "Something went wrong. Please try again later."
const val DEFAULT_ERROR_MESSAGE_NO_INTERNET = "Unable to connect with the server. Please check your internet connection"
const val ERROR_CODE_CANCELLATION_JOB = 451
const val ERROR_CODE_TIMEOUT = HttpURLConnection.HTTP_CLIENT_TIMEOUT
