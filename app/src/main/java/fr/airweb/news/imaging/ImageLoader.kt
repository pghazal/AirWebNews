package fr.airweb.news.imaging

class ImageLoader {

    companion object {
        fun get(): IImageLoader {
            return GlideImageLoader()
        }
    }
}