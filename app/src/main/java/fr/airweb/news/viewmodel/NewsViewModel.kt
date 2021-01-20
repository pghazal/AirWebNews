package fr.airweb.news.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import fr.airweb.news.domain.NewsResult
import fr.airweb.news.network.RestClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel() : ViewModel() {

    companion object {
        private const val TAG = "NewsViewModel"
    }

    private val api = RestClient.createRestClient()

    fun fetchNewsRemote() {
        val call = api.fetchNews()
        call.enqueue(object : Callback<NewsResult> {

            override fun onResponse(call: Call<NewsResult>, response: Response<NewsResult>) {
                if (response.isSuccessful) {
                    Log.d(TAG, response.body().toString())
                }
            }

            override fun onFailure(call: Call<NewsResult>, t: Throwable) {
                // TODO: handle errors
            }

        })
    }
}