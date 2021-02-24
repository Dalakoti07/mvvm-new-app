package com.androiddevs.mvvmnewsapp.api;

import com.androiddevs.mvvmnewsapp.api.responses.NewsResponse
import com.androiddevs.mvvmnewsapp.utils.Constants.Companion.APIKEY
import retrofit2.Response
import retrofit2.http.GET;
import retrofit2.http.Query;

interface NewsApi{

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country") countryCode:String="us",
        @Query("page") page:Int=1,
        @Query("apiKey") apiKey:String=APIKEY
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q") query:String,
        @Query("page") page:Int=1,
        @Query("apiKey") apiKey:String=APIKEY
    ): Response<NewsResponse>

}
