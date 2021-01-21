package fr.airweb.news.network

import fr.airweb.news.model.domain.NewsResult
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {

    @GET("/psg/psg.json")
    fun fetchNews(): Single<NewsResult>
}