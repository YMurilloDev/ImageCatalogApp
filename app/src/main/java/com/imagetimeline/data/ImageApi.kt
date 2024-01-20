package com.imagetimeline.data

import com.imagetimeline.data.responses.ImageResponse
import retrofit2.http.GET

interface ImageApi {
    @GET("rest/?method=flickr.photos.getRecent&format=json&nojsoncallback=1")
    suspend fun fetchImages(): ImageResponse
}


