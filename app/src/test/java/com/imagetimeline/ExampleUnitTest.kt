package com.imagetimeline

import com.imagetimeline.domain.ImageDetail
import com.imagetimeline.domain.ImageRepository
import com.imagetimeline.viewmodels.ImageTimelineState
import com.imagetimeline.viewmodels.ImageTimelineViewModel
import org.junit.Test
import org.junit.Assert.*
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

open class ImageTimelineViewModelTest {

    private val repository: ImageRepository = mockk()
    private val viewModel: ImageTimelineViewModel = ImageTimelineViewModel(repository)

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    open fun beforeEach() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    open fun afterEach() {
        Dispatchers.resetMain()
    }



    @Test
    fun `fetchAndUpdateImages success`() = runBlocking {
        val testImages = listOf(
            ImageDetail(id = "1", imageUrl = "https://example.com/image1.jpg", title = "Image 1"),
            ImageDetail(id = "2", imageUrl = "https://example.com/image2.jpg", title = "Image 2"),
            ImageDetail(id = "3", imageUrl = "https://example.com/image3.jpg", title = "Image 3")
        )
        coEvery { repository.fetchAndUpdateImages() } returns flowOf(testImages)

        viewModel.fetchAndUpdateImages()

        val expectedState = ImageTimelineState(images = testImages, isLoading = false)
        assertEquals(expectedState, viewModel.state.value)
    }

    @Test
    fun `fetchAndUpdateImages error`() = runBlocking {
        coEvery { repository.fetchAndUpdateImages() } throws Exception("Test exception")

        viewModel.fetchAndUpdateImages()

        val expectedState = ImageTimelineState(
            error = R.string.feat_main_fetch_images_error,
            isLoading = false
        )
        assertEquals(expectedState, viewModel.state.value)
    }

    @Test
    fun `fetchAndUpdateImages loading`() = runBlocking {
        val testImages = listOf(
            ImageDetail(id = "1", imageUrl = "https://example.com/image1.jpg", title = "Image 1"),
            ImageDetail(id = "2", imageUrl = "https://example.com/image2.jpg", title = "Image 2"),
            ImageDetail(id = "3", imageUrl = "https://example.com/image3.jpg", title = "Image 3")
        )
        coEvery { repository.fetchAndUpdateImages() } returns flowOf(testImages)

        viewModel.fetchAndUpdateImages()

        val expectedState = ImageTimelineState(isLoading = true)
        assertEquals(expectedState, viewModel.state.value)

        delay(1100)

        val expectedStateAfterLoading = ImageTimelineState(images = testImages, isLoading = false)
        assertEquals(expectedStateAfterLoading, viewModel.state.value)
    }
}
