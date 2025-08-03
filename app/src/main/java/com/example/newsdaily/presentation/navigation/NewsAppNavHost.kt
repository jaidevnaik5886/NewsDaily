package com.example.newsdaily.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newsdaily.R
import com.example.newsdaily.domain.model.Article
import com.example.newsdaily.presentation.newslist.NewsListScreen
import com.example.newsdaily.presentation.newslist.NewsListViewModel
import com.example.newsdaily.presentation.newsview.NewsViewScreen


@Composable
fun NewsAppNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = NavRoutes.NEWS_LIST) {
        composable(NavRoutes.NEWS_LIST) {
            val viewModel: NewsListViewModel = hiltViewModel()
            NewsListScreen(viewModel, navController)
        }

        composable(NavRoutes.NEWS_DETAIL) {
            val article = navController
                .previousBackStackEntry
                ?.savedStateHandle
                ?.get<Article>("article")

            article?.let {
                NewsViewScreen(article = article) {
                    navController.popBackStack()
                }
            } ?: run {
                Text(stringResource(id = R.string.no_article_message))
            }
        }
    }
}
