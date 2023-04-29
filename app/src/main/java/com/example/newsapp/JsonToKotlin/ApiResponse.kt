package com.example.newsapp.JsonToKotlin

data class ApiResponse(
    val status : String,
    val totalResults : Int,
    val articles : ArrayList<NewsArticles>
)
