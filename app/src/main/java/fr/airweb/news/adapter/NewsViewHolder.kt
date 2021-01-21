package fr.airweb.news.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.airweb.news.R
import fr.airweb.news.imaging.ImageLoader
import fr.airweb.news.model.domain.News

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val imageView = itemView.findViewById<ImageView>(R.id.imageView)
    private val titleView = itemView.findViewById<TextView>(R.id.titleView)
    private val contentView = itemView.findViewById<TextView>(R.id.contentView)

    fun bind(news: News) {
        ImageLoader.get()
            .load(news.picture)
            .into(imageView)

        titleView.text = news.title
        contentView.text = news.content
    }
}
