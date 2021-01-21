package fr.airweb.news.model.domain

import com.google.gson.annotations.SerializedName

data class NewsResult(
    @SerializedName("news")
    val news: List<News>
)