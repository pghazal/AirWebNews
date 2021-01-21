package fr.airweb.news.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.airweb.news.model.domain.News
import io.reactivex.Single

@Dao
interface NewsDao {

    @Query("SELECT * FROM news")
    fun getNews(): Single<List<News>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(news: List<News>)
}