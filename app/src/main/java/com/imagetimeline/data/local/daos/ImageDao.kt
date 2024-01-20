package com.imagetimeline.data.local.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.imagetimeline.data.local.entities.ImageEntity

@Dao
interface ImageDao {

    @Upsert
    suspend fun insertImages(images: List<ImageEntity>)

    @Query("SELECT * FROM ImageDetailsEntity")
    suspend fun fetchImages(): List<ImageEntity>

    @Query("DELETE FROM ImageDetailsEntity")
    suspend fun deleteAllImages()

    @Query("SELECT * FROM ImageDetailsEntity WHERE id = :id")
    suspend fun getImageDetail(id: String?): ImageEntity
}
