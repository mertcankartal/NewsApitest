package com.example.newsapitest.repository

import com.example.newsapitest.api.NewsAPI
import javax.inject.Inject

class NewsRemoteData @Inject constructor(val api: NewsAPI) {
    suspend fun getNews(countryCode: String, page: Int, apiKey: String) =
        api.getBreakingNews(countryCode, page, apiKey)
}