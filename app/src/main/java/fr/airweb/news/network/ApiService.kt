package fr.airweb.news.network

import fr.airweb.news.domain.NewsResult
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("/psg/psg.json")
    fun fetchNews(): Call<NewsResult>
}