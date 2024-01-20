package com.imagetimeline.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.imagetimeline.screens.ImageDetailScreen
import com.imagetimeline.screens.ImageTimelineScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = AppScreens.ImageTimelineScreen.route
    ) {
        composable(route = AppScreens.ImageTimelineScreen.route) {
            ImageTimelineScreen { id ->
                navController.navigate(AppScreens.ImageDetailScreen.route + "/$id")
            }
        }
        composable(
            route = AppScreens.ImageDetailScreen.route + "/{id}", arguments = listOf(
                navArgument("id") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: return@composable
            ImageDetailScreen(id)
        }
    }
}