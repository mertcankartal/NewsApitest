package com.example.newsapitest.localRepository

import androidx.lifecycle.LiveData
import com.example.newsapitest.model.Article
import javax.inject.Inject

class NewsLocalDataImpl @Inject constructor(val localData: NewsLocalData) : NewsLocalDataRepository {
    override suspend fun upsertNews(article: Article): Long {
        return localData.upsertNews(article)
    }

    override fun getAllNews(): LiveData<List<Article>> {
        return localData.getArticles()
    }

    override suspend fun deleteArticle(article: Article) {
        return localData.deleteArticle(article)
    }
}