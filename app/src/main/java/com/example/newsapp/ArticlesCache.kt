package com.example.newsapp

import com.example.newsapp.JsonToKotlin.NewsArticles

object ArticlesCache {
    val cacheMap = mutableMapOf<String, ArrayList<NewsArticles>>()
}