package fr.airweb.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import fr.airweb.news.R
import fr.airweb.news.model.domain.News

class NewsAdapter(private val newsClickListener: NewsClickListener) : ListAdapter<News, NewsViewHolder>(DiffUtilCallback) {

    val filter: NewsTitleFilter by lazy {
        NewsTitleFilter(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_news, parent, false)

        return NewsViewHolder(view, newsClickListener)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)
    }

    companion object {
        private val DiffUtilCallback = object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(
                oldItem: News,
                newItem: News
            ): Boolean {
                return oldItem.nid == newItem.nid
            }

            override fun areContentsTheSame(
                oldItem: News,
                newItem: News
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}