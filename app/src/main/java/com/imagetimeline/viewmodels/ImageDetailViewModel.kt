package com.imagetimeline.viewmodels

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imagetimeline.domain.ImageDetail
import com.imagetimeline.domain.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TransactionDetailState(
    val image: ImageDetail? = null,
    val isLoading: Boolean = false,
    @StringRes val error: Int? = null
)

@HiltViewModel
class ImageDetailViewModel @Inject constructor(
    private val repository: ImageRepository
) : ViewModel() {

    private val internalState = MutableStateFlow(TransactionDetailState())
    val state: StateFlow<TransactionDetailState> = internalState

    fun fetchImageDetail(
        id: String?,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            internalState.value = internalState.value.copy(
                isLoading = true
            )
            delay(1000)
            val image = repository.fetchImageDetail(id)
            internalState.value = internalState.value.copy(
                isLoading = false,
                image = image
            )
        }
    }
}
