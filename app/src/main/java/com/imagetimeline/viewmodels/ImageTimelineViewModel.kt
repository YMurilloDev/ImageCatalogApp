package com.imagetimeline.viewmodels

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imagetimeline.R
import com.imagetimeline.domain.ImageRepository
import com.imagetimeline.domain.ImageDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ImageTimelineState(
    val images: List<ImageDetail> = emptyList(),
    val isLoading: Boolean = true,
    @StringRes val error: Int? = null
)

@HiltViewModel
class ImageTimelineViewModel @Inject constructor(
    private val repository: ImageRepository,
) : ViewModel() {

    private val internalSate = MutableStateFlow(ImageTimelineState())
    val state: StateFlow<ImageTimelineState> = internalSate

    fun fetchAndUpdateImages() {
        internalSate.value = internalSate.value.copy(
            isLoading = true
        )
        viewModelScope.launch {
            delay(1000)
            repository.fetchAndUpdateImages().catch {
                internalSate.value = internalSate.value.copy(
                    error = R.string.feat_main_fetch_images_error,
                    isLoading = false
                )
            }.collect { images ->
                internalSate.update {
                    internalSate.value.copy(
                        images = images,
                        isLoading = false,
                    )
                }
            }
        }
    }
}

