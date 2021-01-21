package fr.airweb.news

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import fr.airweb.news.network.RestClient
import fr.airweb.news.repository.AppDatabase
import fr.airweb.news.repository.NewsRepository
import fr.airweb.news.viewmodel.NewsViewModel
import fr.airweb.news.viewmodel.ViewModelFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var newsViewModel: NewsViewModel

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()

        getNews()
    }

    private fun initViewModel() {
        // TODO: use Dagger
        val newsDao = AppDatabase.getDatabase(this).newsDao()
        val api = RestClient.createRestClient()
        val repository = NewsRepository(newsDao, api)
        val viewModelFactory = ViewModelFactory(repository)

        newsViewModel = ViewModelProvider(this, viewModelFactory).get(NewsViewModel::class.java)
    }

    private fun getNews() {
        val disposable = newsViewModel.getNews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.e(TAG, it.toString())
            }, {
                // TODO handle
                Log.e(TAG, it.toString())
            })

        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}