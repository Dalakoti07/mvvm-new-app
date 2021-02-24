package com.androiddevs.mvvmnewsapp.api.responses

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)