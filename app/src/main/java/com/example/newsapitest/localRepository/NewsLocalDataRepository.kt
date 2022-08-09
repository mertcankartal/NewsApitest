package com.example.newsapitest.localRepository

import androidx.lifecycle.LiveData
import com.example.newsapitest.model.Article

interface NewsLocalDataRepository {
    suspend fun upsertNews(article: Article) : Long

    fun getAllNews() : LiveData<List<Article>>

    suspend fun deleteArticle(article: Article)
}