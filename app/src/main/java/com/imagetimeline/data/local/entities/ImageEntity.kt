package com.imagetimeline.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.imagetimeline.domain.ImageDetail

@Entity(tableName = "ImageDetailsEntity")
data class ImageEntity(
    @PrimaryKey
    val id: String,
    val imageUrl: String,
    val title: String,
)

fun ImageEntity.toImageDetail() = ImageDetail(
    id = this.id,
    imageUrl = this.imageUrl,
    title = this.title
)
