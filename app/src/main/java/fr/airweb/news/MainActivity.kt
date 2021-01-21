package fr.airweb.news

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import fr.airweb.news.network.RestClient
import fr.airweb.news.repository.AppDatabase
import fr.airweb.news.repository.NewsRepository
import fr.airweb.news.viewmodel.NewsViewModel
import fr.airweb.news.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var newsViewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showNewsFragment(savedInstanceState)

        initViewModel()
    }

    private fun showNewsFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<NewsFragment>(R.id.fragment_container_view)
            }
        }
    }

    private fun initViewModel() {
        // TODO: use Dagger
        val newsDao = AppDatabase.getDatabase(this).newsDao()
        val api = RestClient.createRestClient()
        val repository = NewsRepository(newsDao, api)
        val viewModelFactory = ViewModelFactory(repository)

        newsViewModel = ViewModelProvider(this, viewModelFactory).get(NewsViewModel::class.java)
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}