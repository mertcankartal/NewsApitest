package com.example.newsapitest.localRepository

import com.example.newsapitest.db.NewsDAO
import com.example.newsapitest.model.Article
import javax.inject.Inject

class NewsLocalData @Inject constructor(val dao: NewsDAO) {
    suspend fun upsertNews(article: Article) = dao.upsert(article)

    fun getArticles() = dao.getAllNews()

    suspend fun deleteArticle(article: Article) = dao.deleteArticle(article)
}