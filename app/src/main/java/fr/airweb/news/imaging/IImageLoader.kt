package fr.airweb.news.imaging

import android.widget.ImageView
import androidx.annotation.DrawableRes

interface IImageLoader {

    fun load(url: String?): IImageLoader

    fun placeholder(@DrawableRes placeholderResId: Int): IImageLoader

    fun into(imageView: ImageView?)
}