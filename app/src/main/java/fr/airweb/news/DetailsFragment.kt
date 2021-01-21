package fr.airweb.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import fr.airweb.news.databinding.FragmentDetailsBinding
import fr.airweb.news.imaging.ImageLoader
import fr.airweb.news.model.domain.News
import fr.airweb.news.utils.Arguments
import fr.airweb.news.viewmodel.NewsViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailsFragment : BaseFragment(R.layout.fragment_details) {

    private var _viewBinding: FragmentDetailsBinding? = null
    private val viewBinding get() = _viewBinding!!

    private val newsViewModel: NewsViewModel by activityViewModels()

    private var newsId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        arguments?.let {
            parseArguments(it)
        } ?: kotlin.run {
            parseArguments(savedInstanceState)
        }
    }

    private fun parseArguments(args: Bundle?) {
        if (args == null) {
            return
        }

        newsId = args.getInt(Arguments.ARGS_NEWS_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentDetailsBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val disposable = newsViewModel.getNewsById(newsId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                updateViews(viewBinding, it)
            }, {

            })

        compositeDisposable.add(disposable)
    }

    private fun updateViews(viewBinding: FragmentDetailsBinding, news: News) {
        ImageLoader.get()
            .load(news.picture)
            .into(viewBinding.imageView)

        viewBinding.titleView.text = news.title
        viewBinding.contentView.text = news.content
    }
}