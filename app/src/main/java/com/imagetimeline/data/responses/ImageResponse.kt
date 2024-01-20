package com.imagetimeline.data.responses

import com.google.gson.annotations.SerializedName
import com.imagetimeline.data.local.entities.ImageEntity


data class ImageResponse(
    @SerializedName("photos")
    val images: Photos,
)

data class Photos(
    val page: Int,
    val pages: Int,
    @SerializedName("perpage")
    val perPage: Int,
    val total: Int,
    val photo: List<ImageInfo>
)

data class ImageInfo(
    val id: String,
    val secret: String,
    val server: String,
    val title: String,
)

fun ImageResponse.toImageEntityList(): List<ImageEntity> {
    return images.photo.map { imageInfo ->
        ImageEntity(
            imageUrl = buildImageUrl(
                server = imageInfo.server,
                id = imageInfo.id,
                secret = imageInfo.secret
            ),
            title = imageInfo.title,
            id = imageInfo.id
        )
    }
}

fun buildImageUrl(server: String, id: String, secret: String): String {
    return "https://live.staticflickr.com/$server/${id}_${secret}_b.jpg"
}


