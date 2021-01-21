package fr.airweb.news.viewmodel

import androidx.lifecycle.ViewModel
import fr.airweb.news.model.SortBy
import fr.airweb.news.model.domain.News
import fr.airweb.news.model.domain.NewsType
import fr.airweb.news.repository.NewsRepository
import io.reactivex.Observable
import java.text.SimpleDateFormat
import java.util.*

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    private var newsTypeFilter: NewsType = NewsType.NEWS
    private var sortBy: SortBy = SortBy.NONE

    fun getNews(): Observable<List<News>> {
        return newsRepository.getNews()
            .flatMap {
                Observable.fromIterable(it)
            }
            .filter { news -> news.type == newsTypeFilter }
            .distinct()
            .toSortedList { first, second ->
                when (sortBy) {
                    SortBy.TITLE -> {
                        first.title.compareTo(second.title)
                    }
                    SortBy.DATE -> {
                        val date1 = parseToDate(first.date)
                        val date2 = parseToDate(second.date)

                        date1?.compareTo(date2) ?: 0
                    }
                    else -> {
                        // No sort, back to normal: meaning just like data from api
                        0
                    }
                }
            }
            .toObservable()
    }

    fun setFilter(newsFilter: NewsType) {
        this.newsTypeFilter = newsFilter
    }

    fun sortBy(sortBy: SortBy) {
        this.sortBy = sortBy
    }

    private fun parseToDate(strDate: String): Date? {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return format.parse(strDate)
    }
}