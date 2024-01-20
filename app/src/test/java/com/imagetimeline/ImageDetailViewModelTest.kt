@file:OptIn(ExperimentalCoroutinesApi::class)

package com.imagetimeline


import app.cash.turbine.test
import com.imagetimeline.domain.ImageDetail
import com.imagetimeline.domain.ImageRepository
import com.imagetimeline.viewmodels.ImageDetailViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
class ImageDetailViewModelTest {

    private lateinit var viewModel: ImageDetailViewModel

    private val repository: ImageRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ImageDetailViewModel(repository)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchImageDetail success`() = runTest {
        val testId = "1"
        val testImage = ImageDetail(id = "1", imageUrl = "https://example.com/image1.jpg", title = "Image 1")

        coEvery { repository.fetchImageDetail(testId) } returns testImage

        viewModel.fetchImageDetail(testId)

        viewModel.state.test {
            val loadingState = awaitItem()
            assertEquals(true, loadingState.isLoading)

            val successState = awaitItem()
            assertEquals(testImage, successState.image)
            assertEquals(false, successState.isLoading)
            assertEquals(null, successState.error)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `fetchImageDetail error`() = runTest {
        val exception = Exception("Test exception")
        coEvery { repository.fetchImageDetail(any()) } throws exception

        viewModel.fetchImageDetail("1")

        viewModel.state.test {
            val loadingState = awaitItem()
            assertEquals(true, loadingState.isLoading)

            val errorState = awaitItem()
            assertEquals(null, errorState.image)
            assertEquals(false, errorState.isLoading)

            assertEquals(R.string.feat_main_fetch_image_error, errorState.error)

            cancelAndConsumeRemainingEvents()
        }
    }
}