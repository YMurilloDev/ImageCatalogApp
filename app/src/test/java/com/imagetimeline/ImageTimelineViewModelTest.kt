package com.imagetimeline

import app.cash.turbine.test
import com.imagetimeline.domain.ImageDetail
import com.imagetimeline.domain.ImageRepository
import com.imagetimeline.viewmodels.ImageTimelineViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
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
class ImageTimelineViewModelTest {

    private lateinit var viewModel: ImageTimelineViewModel

    private val repository: ImageRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ImageTimelineViewModel(repository)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchAndUpdateImages success`() = runTest {
        val testImages = listOf(
            ImageDetail(id = "1", imageUrl = "https://example.com/image1.jpg", title = "Image 1"),
            ImageDetail(id = "2", imageUrl = "https://example.com/image2.jpg", title = "Image 2"),
            ImageDetail(id = "3", imageUrl = "https://example.com/image3.jpg", title = "Image 3")
        )
        coEvery { repository.fetchAndUpdateImages() } returns flowOf(testImages)

        viewModel.fetchAndUpdateImages()

        viewModel.state.test {
            val loadingState = awaitItem()
            assertEquals(true, loadingState.isLoading)

            val successState = awaitItem()
            assertEquals(testImages, successState.images)
            assertEquals(false, successState.isLoading)
            assertEquals(null, successState.error)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `fetchAndUpdateImages error`() = runTest {
        val exception = Exception("Test exception")
        coEvery { repository.fetchAndUpdateImages() } returns flow {
            throw exception
        }

        viewModel.fetchAndUpdateImages()

        viewModel.state.test {
            val loadingState = awaitItem()
            assertEquals(true, loadingState.isLoading)

            val errorState = awaitItem()
            assertEquals(emptyList<ImageDetail>(), errorState.images)
            assertEquals(false, errorState.isLoading)

            assertEquals(R.string.feat_main_fetch_images_error, errorState.error)

            cancelAndConsumeRemainingEvents()
        }
    }
}