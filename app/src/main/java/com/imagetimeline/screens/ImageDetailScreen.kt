package com.imagetimeline.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.imagetimeline.viewmodels.ImageDetailViewModel


@Composable
fun ImageDetailScreen(id: String) {
    val viewModel: ImageDetailViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    LaunchedEffect(true) {
        viewModel.fetchImageDetail(id)
    }
    ImageDetail(title = state.image?.title ?: "", url = state.image?.imageUrl ?: "")
}

@Composable
fun ImageDetail(title: String, url: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.titleMedium
        )
        AsyncImage(
            model = url,
            contentDescription = "content description",
            modifier = Modifier
                .fillMaxSize()
        )
    }
}