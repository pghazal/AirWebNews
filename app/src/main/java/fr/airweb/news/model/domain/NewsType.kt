package fr.airweb.news.model.domain

import com.google.gson.annotations.SerializedName

enum class NewsType(val value: String) {
    @SerializedName("news")
    NEWS("news"),

    @SerializedName("actualité")
    ACTUALITY("actualité"),

    @SerializedName("hot")
    HOT("hot")
}