package com.example.newsapitest.repository

import com.example.newsapitest.api.NewsAPI
import com.example.newsapitest.model.NewsResponse
import com.example.newsapitest.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface NewsRepository {
    suspend fun getNews(countryCode: String, page: Int) : NewsResponse

    suspend fun searchNews(search:String,page: Int) : NewsResponse
}