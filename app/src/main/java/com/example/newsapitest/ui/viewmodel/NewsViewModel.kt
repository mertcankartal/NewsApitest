package com.example.newsapitest.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapitest.localRepository.NewsLocalDataRepository
import com.example.newsapitest.model.Article
import com.example.newsapitest.model.NewsResponse
import com.example.newsapitest.repository.NewsRepository
import com.example.newsapitest.utils.Constants
import com.example.newsapitest.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject
import kotlin.Exception

@HiltViewModel
class NewsViewModel @Inject constructor(
    val remoteRepository: NewsRepository,
    val localDataRepository: NewsLocalDataRepository
) : ViewModel() {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var newsPage = 2

    val searchedNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1

    init {
        getNews("tr", newsPage, Constants.API_KEY)
    }

    fun getNews(countryCode: String, page: Int, apiKey: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        try {
            breakingNews.value =
                Resource.Success(remoteRepository.getNews(countryCode, newsPage, apiKey))
        } catch (e: Exception) {
            breakingNews.value = Resource.Error(e.message.toString())
        }
    }

    fun searchNews(searchNews: String) = viewModelScope.launch {
        searchedNews.postValue(Resource.Loading())
        try {
            searchedNews.value =
                Resource.Success(remoteRepository.searchNews(searchNews, searchNewsPage))
        } catch (e: Exception) {
            searchedNews.value = Resource.Error(e.message.toString())
        }
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        localDataRepository.upsertNews(article)
    }

    fun getSavedArticles() =  localDataRepository.getAllNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        localDataRepository.deleteArticle(article)
    }



}

//eğer retrofit response kullansaydık try catch kullanmadan bu fun ile handle edebilirdik.
/*private fun handleResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
    if (response.isSuccessful) {
        response.body()?.let {
            return Resource.Success(it)
        }
    }
    return Resource.Error(response.message())
}*/