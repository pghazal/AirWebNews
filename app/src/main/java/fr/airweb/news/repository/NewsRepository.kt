package fr.airweb.news.repository

import fr.airweb.news.model.domain.News
import fr.airweb.news.network.ApiService
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class NewsRepository(private val newsDao: NewsDao, private val api: ApiService) {

    private fun getNewsFromDb(): Observable<List<News>> {
        return newsDao.getNews()
            .filter { it.isNotEmpty() }
            .toObservable()
    }

    private fun fetchNews(): Observable<ArrayList<News>> {
        return api.fetchNews()
            .map { result ->
                val news = ArrayList<News>()
                result.news.forEach {
                    news.add(it)
                }
                return@map news
            }
            .doOnSuccess {
                storeNewsInDb(it)
            }
            .onErrorReturn {
                ArrayList()
            }
            .toObservable()
    }

    fun getNews(): Observable<List<News>> {
        return Observable.concatArray(getNewsFromDb(), fetchNews())
    }

    private fun storeNewsInDb(news: List<News>) {
        Observable.fromCallable {
            newsDao.insertAll(news)
        }.subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe()
    }

    fun getNewsById(id: Int): Observable<News> {
        return newsDao.getNewsById(id).toObservable()
    }
}