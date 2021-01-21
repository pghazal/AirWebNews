package fr.airweb.news

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import fr.airweb.news.adapter.NewsAdapter
import fr.airweb.news.databinding.FragmentNewsBinding
import fr.airweb.news.model.domain.News
import fr.airweb.news.model.domain.NewsType
import fr.airweb.news.viewmodel.NewsViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NewsFragment : BaseFragment(R.layout.fragment_news), SwipeRefreshLayout.OnRefreshListener {

    private var _viewBinding: FragmentNewsBinding? = null
    private val viewBinding get() = _viewBinding!!

    private lateinit var adapter: NewsAdapter

    private val newsViewModel: NewsViewModel by activityViewModels()
    private lateinit var newsObservable: Observable<List<News>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentNewsBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureViews(viewBinding)

        getAndDisplayNews()
    }

    private fun configureViews(viewBinding: FragmentNewsBinding) {
        viewBinding.swipeRefreshLayout.setOnRefreshListener(this)

        adapter = NewsAdapter()
        configureRecyclerView(viewBinding)
    }

    private fun configureRecyclerView(viewBinding: FragmentNewsBinding) {
        viewBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        viewBinding.recyclerView.adapter = adapter

        // Set initial filter
        newsViewModel.setFilter(NewsType.NEWS)

        newsObservable = newsViewModel.getNews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                showLoader(true)
            }
            .doOnComplete {
                showLoader(false)
            }
            .doOnError {
                showLoader(false)
            }
    }

    override fun onRefresh() {
        getAndDisplayNews()
    }

    private fun getAndDisplayNews() {
        val disposable = newsObservable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                adapter.submitList(it)
            }, {
                // TODO handle
                Log.e(TAG, it.toString())
            })

        compositeDisposable.add(disposable)
    }

    private fun showLoader(show: Boolean) {
        viewBinding.swipeRefreshLayout.isRefreshing = show
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_news, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter_news -> {
                newsViewModel.setFilter(NewsType.NEWS)
                getAndDisplayNews()
                return true
            }

            R.id.filter_actuality -> {
                newsViewModel.setFilter(NewsType.ACTUALITY)
                getAndDisplayNews()
                return true
            }

            R.id.filter_hot -> {
                newsViewModel.setFilter(NewsType.HOT)
                getAndDisplayNews()
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    companion object {
        private const val TAG = "NewsFragment"
    }
}