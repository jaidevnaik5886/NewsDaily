package com.example.newsdaily.presentation.newslist


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsdaily.domain.model.Article
import com.example.newsdaily.domain.repository.NewsRepository
import com.example.newsdaily.network.DEFAULT_ERROR_MESSAGE
import com.example.newsdaily.network.Result
import com.example.newsdaily.network.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val _newsState = MutableStateFlow<ViewState<List<Article>>>(ViewState.Loading)
    val newsState: StateFlow<ViewState<List<Article>>> = _newsState

    init {
        fetchTopHeadlines()
    }

    fun fetchTopHeadlines() {
        _newsState.value = ViewState.Loading
        viewModelScope.launch {
            when (val result = repository.getTopHeadlines()) {
                is Result.Success -> {
                    result.data.articles?.let {
                        _newsState.value = ViewState.Data(it)
                    }
                }
                is Result.SuccessWithNoContent -> _newsState.value = ViewState.Error(
                    DEFAULT_ERROR_MESSAGE
                )
                is Result.Error -> _newsState.value = ViewState.Error(result.error.message)
            }
        }
    }
}
