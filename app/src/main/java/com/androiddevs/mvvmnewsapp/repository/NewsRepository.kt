package com.androiddevs.mvvmnewsapp.repository

import com.androiddevs.mvvmnewsapp.api.RetrofitInstance
import com.androiddevs.mvvmnewsapp.api.responses.Article
import com.androiddevs.mvvmnewsapp.db.ArticleDatabase

class NewsRepository(
    val db:ArticleDatabase
) {

    suspend fun getBreakingNews(countryCode:String ,pageNumber:Int)=
        RetrofitInstance.apiInstance.getBreakingNews(countryCode,pageNumber)

    suspend fun searchForNews(query:String,pageNumber: Int)=
        RetrofitInstance.apiInstance.searchForNews(query,pageNumber)

    suspend fun insertArticle(article:Article)=db.getArticleDao().insert(article)

    fun getSavedNews()=db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) =db.getArticleDao().deleteArticle(article)
}