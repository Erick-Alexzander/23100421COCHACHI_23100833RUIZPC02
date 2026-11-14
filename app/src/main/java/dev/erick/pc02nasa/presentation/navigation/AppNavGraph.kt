package dev.erick.pc02nasa.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dev.erick.pc02nasa.presentation.asteroids.AsteroidViewModel
import dev.erick.pc02nasa.presentation.asteroids.detail.AsteroidDetailScreen
import dev.erick.pc02nasa.presentation.asteroids.favorites.AsteroidFavoritesScreen
import dev.erick.pc02nasa.presentation.asteroids.list.AsteroidScreen
import dev.erick.pc02nasa.ui.ApodDetailScreen
import dev.erick.pc02nasa.ui.ApodFavoritesScreen
import dev.erick.pc02nasa.ui.ApodScreen
import dev.erick.pc02nasa.viewmodel.ApodViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    asteroidViewModel: AsteroidViewModel,
    apodViewModel: ApodViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "apod",
        modifier = modifier
    ) {
        // ==========================================
        // MÓDULO APOD (KIARA)
        // ==========================================
        composable("apod") {
            ApodScreen(
                viewModel = apodViewModel,
                onItemClick = { item ->
                    val date = item.date ?: ""
                    if (date.isNotBlank()) {
                        navController.navigate("apod_detail/$date")
                    }
                },
                onFavoritesClick = {
                    navController.navigate("apod_favorites")
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        composable("apod_favorites") {
            ApodFavoritesScreen(
                viewModel = apodViewModel,
                onItemClick = { item ->
                    val date = item.date ?: ""
                    if (date.isNotBlank()) {
                        navController.navigate("apod_detail/$date")
                    }
                },
                onBackClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxSize()
            )
        }

        composable(
            route = "apod_detail/{date}",
            arguments = listOf(navArgument("date") { type = NavType.StringType })
        ) { backStackEntry ->
            val date = backStackEntry.arguments?.getString("date") ?: ""
            ApodDetailScreen(
                date = date,
                viewModel = apodViewModel,
                onBackClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxSize()
            )
        }

        // ==========================================
        // MÓDULO ASTEROIDES (ERICK)
        // ==========================================
        composable("asteroids") {
            AsteroidScreen(
                viewModel = asteroidViewModel,
                onNavigateToDetail = { id ->
                    navController.navigate("asteroid_detail/$id")
                },
                onNavigateToFavorites = {
                    navController.navigate("asteroid_favorites")
                }
            )
        }

        composable("asteroid_detail/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: return@composable
            AsteroidDetailScreen(
                asteroidId = id,
                viewModel = asteroidViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("asteroid_favorites") {
            AsteroidFavoritesScreen(
                viewModel = asteroidViewModel,
                onNavigateToDetail = { id ->
                    navController.navigate("asteroid_detail/$id")
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
