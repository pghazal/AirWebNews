package fr.airweb.news.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import fr.airweb.news.R
import fr.airweb.news.imaging.ImageLoader
import fr.airweb.news.model.domain.News

class NewsViewHolder(itemView: View, private val newsClickListener: NewsClickListener) :
    RecyclerView.ViewHolder(itemView) {

    private val cardView = itemView.findViewById<CardView>(R.id.cardView)
    private val imageView = itemView.findViewById<ImageView>(R.id.imageView)
    private val titleView = itemView.findViewById<TextView>(R.id.titleView)
    private val contentView = itemView.findViewById<TextView>(R.id.contentView)

    fun bind(news: News) {
        ImageLoader.get()
            .load(news.picture)
            .into(imageView)

        titleView.text = news.title
        contentView.text = news.content

        cardView.setOnClickListener {
            newsClickListener.onNewsClicked(news)
        }
    }
}
