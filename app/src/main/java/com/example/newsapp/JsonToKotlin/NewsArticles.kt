package com.example.newsapp.JsonToKotlin

import java.io.Serializable

data class NewsArticles(
    val author : String?,
    val title: String,
    val description : String?,
    val url : String?,
    val urlToImage : String?,
    val publishedAt : String,
    val content : String?,
    val source : NewsSource
) : Serializable
