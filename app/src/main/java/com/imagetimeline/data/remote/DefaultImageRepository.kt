package com.imagetimeline.data.remote

import com.imagetimeline.data.ImageApi
import com.imagetimeline.domain.ImageRepository
import com.imagetimeline.data.local.daos.ImageDao
import com.imagetimeline.data.local.entities.toImageDetail
import com.imagetimeline.data.responses.toImageEntityList
import com.imagetimeline.domain.ImageDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DefaultImageRepository(
    private val api: ImageApi,
    private val dao: ImageDao
) : ImageRepository {
    override suspend fun fetchAndUpdateImages() = flow {
        try {
            var images = dao.fetchImages().map { it.toImageDetail() }
            if (images.isNotEmpty()) {
                emit(images)
            }
            val response = api.fetchImages()
            val apiImages = response.toImageEntityList()

            if (apiImages.isNotEmpty()) {
                dao.deleteAllImages()
                dao.insertImages(apiImages)
            }
            images = dao.fetchImages().map { it.toImageDetail() }
            emit(images)
        } catch (ex: Exception) {
            println("Error en la llamada")
            ex.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun fetchImageDetail(id: String?): ImageDetail {
        return dao.getImageDetail(id).toImageDetail()
    }
}
