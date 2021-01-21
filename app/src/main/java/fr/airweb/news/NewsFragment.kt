package fr.airweb.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import fr.airweb.news.adapter.NewsAdapter
import fr.airweb.news.databinding.FragmentNewsBinding
import fr.airweb.news.viewmodel.NewsViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NewsFragment : BaseFragment(R.layout.fragment_news) {

    private var _viewBinding: FragmentNewsBinding? = null
    private val viewBinding get() = _viewBinding!!

    private lateinit var adapter: NewsAdapter

    private val newsViewModel: NewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentNewsBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    private fun configureViews(viewBinding: FragmentNewsBinding) {
        adapter = NewsAdapter()

        viewBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        viewBinding.recyclerView.adapter = adapter
    }

    private fun subscribeNewsViewModel() {
        val disposable = newsViewModel.getNews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                adapter.submitList(it)
            }, {
                // TODO handle
                Log.e(TAG, it.toString())
            })

        compositeDisposable.add(disposable)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureViews(viewBinding)

        subscribeNewsViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    companion object {
        private const val TAG = "NewsFragment"
    }
}