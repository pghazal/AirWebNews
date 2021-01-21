package fr.airweb.news.repository.converter

import androidx.room.TypeConverter
import fr.airweb.news.model.domain.NewsType

class RoomTypeConverters {

    @TypeConverter
    fun deserializeNewsType(value: Int): NewsType {
        return NewsType.values()[value]
    }

    @TypeConverter
    fun serializeNewsType(value: NewsType): Int {
        return value.ordinal
    }
}