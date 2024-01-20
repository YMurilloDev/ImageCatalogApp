package com.imagetimeline

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.imagetimeline.delegates.ImageLoaderDelegate
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ImageTimeLineApp : Application(), ImageLoaderFactory {

    private val imageLoaderDelegate by lazy { ImageLoaderDelegate(this) }

    override fun newImageLoader(): ImageLoader {
        return imageLoaderDelegate.newImageLoader()
    }
}