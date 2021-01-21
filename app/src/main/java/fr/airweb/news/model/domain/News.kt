package fr.airweb.news.model.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import fr.airweb.news.repository.converter.RoomTypeConverters

@Entity(tableName = "news")
@TypeConverters(RoomTypeConverters::class)
data class News(
    @PrimaryKey
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