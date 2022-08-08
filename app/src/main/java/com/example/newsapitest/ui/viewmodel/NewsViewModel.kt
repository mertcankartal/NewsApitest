package com.example.newsapitest.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapitest.model.NewsResponse
import com.example.newsapitest.repository.NewsRepository
import com.example.newsapitest.utils.Constants
import com.example.newsapitest.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(val repository: NewsRepository) : ViewModel() {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var newsPage = 1

    init {
        getNews("tr",newsPage,Constants.API_KEY)
    }

    fun getNews(countryCode:String,page:Int,apiKey:String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        try {
            breakingNews.value = Resource.Success(repository.getNews(countryCode, newsPage, apiKey))
        }catch (e:Exception){
            breakingNews.value = Resource.Error(e.message.toString())
        }
    }

    //eğer retrofit response kullansaydık try catch kullanmadan bu fun ile handle edebilirdik.
    private fun handleResponse(response: Response<NewsResponse>) : Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
            return Resource.Error(response.message())
    }


}