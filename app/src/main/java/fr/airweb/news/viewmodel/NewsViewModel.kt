package fr.airweb.news.viewmodel

import androidx.lifecycle.ViewModel
import fr.airweb.news.model.domain.News
import fr.airweb.news.repository.NewsRepository
import io.reactivex.Observable

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    fun getNews(): Observable<List<News>> {
        return newsRepository.getNews()
    }
}