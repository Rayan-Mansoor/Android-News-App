package com.example.newsapp.JsonToKotlin

import java.io.Serializable

data class NewsSource(
    val id : String,
    val name : String
) : Serializable
