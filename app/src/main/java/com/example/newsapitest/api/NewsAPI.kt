package com.example.newsapitest.api

import com.example.newsapitest.model.NewsResponse
import com.example.newsapitest.utils.Constants.Companion.API_KEY
import com.example.newsapitest.utils.Constants.Companion.COUNTRY_CODE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country") countryCode : String = COUNTRY_CODE,
        @Query("pageSize") page : Int = 30,
        @Query("apiKey") apiKey : String = API_KEY
    ):NewsResponse

    @GET("v2/everything")
    suspend fun searchNews(
        @Query("q") query : String,
        @Query("page") page : Int = 1,
        @Query("apiKey") apiKey : String = API_KEY
    ):NewsResponse
}