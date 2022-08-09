package com.example.newsapitest.repository

import com.example.newsapitest.api.NewsAPI
import javax.inject.Inject

class NewsRemoteData @Inject constructor(val api: NewsAPI) {
    suspend fun getNews(countryCode: String, page: Int) =
        api.getBreakingNews(countryCode, page)

    suspend fun searchNews(search: String, page: Int) = api.searchNews(search, page)
}