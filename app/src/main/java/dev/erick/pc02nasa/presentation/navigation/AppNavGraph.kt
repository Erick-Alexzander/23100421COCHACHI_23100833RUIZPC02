package dev.erick.pc02nasa.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.erick.pc02nasa.presentation.asteroids.AsteroidViewModel
import dev.erick.pc02nasa.presentation.asteroids.detail.AsteroidDetailScreen
import dev.erick.pc02nasa.presentation.asteroids.favorites.AsteroidFavoritesScreen
import dev.erick.pc02nasa.presentation.asteroids.list.AsteroidScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    asteroidViewModel: AsteroidViewModel,
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
            // Kiara: Aquí integrarás tu pantalla principal de APOD.
            // Ejemplo: ApodScreen(viewModel = apodViewModel)
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Módulo Imágenes APOD pendiente de integración")
            }
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
