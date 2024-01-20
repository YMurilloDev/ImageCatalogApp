package com.imagetimeline.domain

import kotlinx.coroutines.flow.Flow

interface ImageRepository {

    suspend fun fetchAndUpdateImages(): Flow<List<ImageDetail>>
    suspend fun fetchImageDetail(id: String?): ImageDetail
}