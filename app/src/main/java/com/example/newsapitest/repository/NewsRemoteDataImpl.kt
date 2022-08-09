package com.example.newsapitest.repository

import com.example.newsapitest.model.NewsResponse
import javax.inject.Inject

class NewsRemoteDataImpl @Inject constructor(val remoteData: NewsRemoteData) : NewsRepository {
    override suspend fun getNews(countryCode: String, page: Int): NewsResponse {
        return remoteData.getNews(countryCode,page)
    }

    override suspend fun searchNews(search: String, page: Int): NewsResponse {
        return remoteData.searchNews(search,page)
    }
}