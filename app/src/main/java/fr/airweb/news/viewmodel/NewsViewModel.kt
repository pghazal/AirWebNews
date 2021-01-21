package fr.airweb.news.viewmodel

import androidx.lifecycle.ViewModel
import fr.airweb.news.model.domain.News
import fr.airweb.news.model.domain.NewsType
import fr.airweb.news.repository.NewsRepository
import io.reactivex.Observable

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    fun getNews(newsType: NewsType): Observable<List<News>> {
        return newsRepository.getNews()
            .flatMap {
                Observable.fromIterable(it)
            }
            .filter { news -> news.type == newsType }
            .distinct()
            .toList()
            .toObservable()
    }
}