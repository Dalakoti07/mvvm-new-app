package com.androiddevs.mvvmnewsapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.mvvmnewsapp.api.responses.Article
import com.androiddevs.mvvmnewsapp.api.responses.NewsResponse
import com.androiddevs.mvvmnewsapp.api.responses.Resource
import com.androiddevs.mvvmnewsapp.repository.NewRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    val newsRepository: NewRepository
) :ViewModel(){
    val breakingNews:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage=1

    val searchNews:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage=1

    init {
        getBreakingNews("us")
    }

    fun getBreakingNews(countryCode:String)=viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response=newsRepository.getBreakingNews(countryCode,pageNumber = breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>):Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let { resultResponse->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun searchNews(searchQuery:String)=viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response=newsRepository.searchForNews(query = searchQuery,pageNumber = searchNewsPage)
        searchNews.postValue(handleSearchNewsResponse(response))
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>):Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let { resultResponse->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveArticle(article: Article) =viewModelScope.launch {
        newsRepository.insertArticle(article)
    }

    fun getSavedNews()=newsRepository.getSavedNews()

    fun deleteArticle(article: Article) =viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

}