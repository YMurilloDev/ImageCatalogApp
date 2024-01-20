package com.imagetimeline.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.imagetimeline.R
import com.imagetimeline.domain.ImageDetail
import com.imagetimeline.viewmodels.ImageTimelineViewModel

@Composable
fun ImageTimelineScreen(navigateToDetail: (String) -> Unit) {

    val viewModel: ImageTimelineViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(true) {
        viewModel.fetchAndUpdateImages()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .wrapContentSize(Alignment.Center)
                )
            }

            !state.isLoading && state.images.isEmpty() -> {
                Text(
                    text = stringResource(id = R.string.feat_main_fetch_images_error),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .wrapContentSize(Alignment.Center)
                )
            }

            else -> {
                ImageList(images = state.images, clickImage = { navigateToDetail(it) })
            }
        }
    }
}

@Composable
fun ImageList(images: List<ImageDetail>, clickImage: (id: String) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(
            items = images,
            key = {
                it.id
            }
        ) { image ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .clickable {
                        clickImage(image.id)
                    }
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = image.title,
                    style = MaterialTheme.typography.titleMedium
                )
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    model = image.imageUrl,
                    contentDescription = "content description"
                )
            }
        }
    }
}