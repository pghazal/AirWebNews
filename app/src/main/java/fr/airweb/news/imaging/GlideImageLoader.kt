package fr.airweb.news.imaging

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class GlideImageLoader : IImageLoader {

    private var url: String? = null
    private var placeholderResId: Int? = null

    override fun load(url: String?): IImageLoader {
        this.url = url
        return this
    }

    override fun placeholder(placeholderResId: Int): IImageLoader {
        this.placeholderResId = placeholderResId
        return this
    }

    override fun into(imageView: ImageView?) {
        imageView?.let {
            val requestManager = Glide.with(imageView.context)

            var options = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)

            placeholderResId?.let { resId ->
                options = options.placeholder(resId)
            }

            requestManager.load(url)
                .apply(options)
                .into(imageView)
        }
    }
}