package com.imagetimeline.navigation

sealed class AppScreens(val route: String) {
    object ImageTimelineScreen : AppScreens("image_timeline_screen")
    object ImageDetailScreen : AppScreens("image_detail_screen")
}
