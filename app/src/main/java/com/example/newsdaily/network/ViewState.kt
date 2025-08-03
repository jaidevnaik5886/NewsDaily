package com.example.newsdaily.network

sealed class ViewState<out T : Any> {
    data object Loading : ViewState<Nothing>()
    data class Data<out T : Any>(val data: T) : ViewState<T>()
    data class Error(val error: String, val code: Int? = null) : ViewState<Nothing>()
}
