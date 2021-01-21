package fr.airweb.news.viewmodel

import androidx.lifecycle.ViewModel
import fr.airweb.news.model.domain.News
import fr.airweb.news.model.domain.NewsType
import fr.airweb.news.repository.NewsRepository
import io.reactivex.Observable

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    private var newsTypeFilter: NewsType = NewsType.NEWS

    fun getNews(): Observable<List<News>> {
        return newsRepository.getNews()
            .flatMap {
                Observable.fromIterable(it)
            }
            .filter { news -> news.type == newsTypeFilter }
            .distinct()
            .toList()
            .toObservable()
    }

    fun setFilter(newsFilter: NewsType) {
        this.newsTypeFilter = newsFilter
    }
}