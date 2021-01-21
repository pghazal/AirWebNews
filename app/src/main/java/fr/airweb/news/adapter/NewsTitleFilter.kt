package fr.airweb.news.adapter

import android.widget.Filter
import fr.airweb.news.model.domain.News
import java.util.*

class NewsTitleFilter(private val adapter: NewsAdapter) : Filter() {

    lateinit var items: List<News>

    override fun performFiltering(constraint: CharSequence): FilterResults {
        var constraint: CharSequence? = constraint
        val results = FilterResults()

        if (constraint != null && constraint.isNotEmpty()) {
            constraint = constraint.toString().toUpperCase(Locale.getDefault())

            val filteredNews = ArrayList<News>()

            items.forEach {
                if (it.title.toUpperCase(Locale.getDefault()).contains(constraint)) {
                    filteredNews.add(it)
                }
            }

            results.count = filteredNews.size
            results.values = filteredNews
        } else {
            results.count = items.size
            results.values = items
        }

        return results
    }

    override fun publishResults(constraint: CharSequence, results: FilterResults) {
        if (results.values == null) {
            return
        }

        adapter.submitList(results.values as List<News>)
    }
}