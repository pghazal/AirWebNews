package fr.airweb.news.adapter

import fr.airweb.news.model.domain.News

interface NewsClickListener {

    fun onNewsClicked(news: News)
}