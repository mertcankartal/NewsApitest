package com.example.newsapitest.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsapitest.model.Article
import com.example.newsapitest.model.NewsResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

@Dao
interface NewsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article) : Long

    @Query("select * from articles")
     fun getAllNews() : LiveData<List<Article>>

     @Query("select * from articles")
     fun getAllNewsFlow() : Flow<List<Article>>

     @Delete
     fun deleteArticle(article: Article)
}