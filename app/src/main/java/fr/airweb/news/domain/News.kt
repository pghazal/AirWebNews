package fr.airweb.news.domain

import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("nid")
    val nid: Int,
    @SerializedName("type")
    val type: NewsType,
    @SerializedName("date")
    val date: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("picture")
    val picture: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("dateformated")
    val dateFormated: String
)