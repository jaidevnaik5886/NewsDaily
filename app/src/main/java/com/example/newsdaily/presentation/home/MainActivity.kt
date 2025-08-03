package com.example.newsdaily.presentation.home

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.newsdaily.base.BaseActivity
import com.example.newsdaily.presentation.navigation.NewsAppNavHost
import com.example.newsdaily.ui.theme.NewsDailyAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsDailyAppTheme {
                val navController = rememberNavController()
                NewsAppNavHost(navController = navController)
            }
        }
    }
}