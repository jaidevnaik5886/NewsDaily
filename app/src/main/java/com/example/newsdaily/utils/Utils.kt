package com.example.newsdaily.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone


fun isValidString(value: String?): Boolean = !value.isNullOrEmpty() && value != "null"


@SuppressLint("SimpleDateFormat")
fun String.toTimeAgo(): String {
    return try {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        format.timeZone = TimeZone.getTimeZone("UTC")
        val past = format.parse(this)
        val now = Date()
        val seconds = (now.time - past.time) / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        when {
            seconds < 60 -> "$seconds sec ago"
            minutes < 60 -> "$minutes min ago"
            hours < 24 -> "$hours hr ago"
            else -> "$days day ago"
        }
    } catch (e: Exception) {
        ""
    }
}