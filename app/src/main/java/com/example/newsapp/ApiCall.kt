package com.example.newsapp

import com.example.newsapp.JsonToKotlin.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiCall {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country")
        country : String,

        @Query("category")
        category : String,

        @Query("q")
        query : String?,

        @Query("apiKey")
        apiKey : String

    ) : Response<ApiResponse>


}