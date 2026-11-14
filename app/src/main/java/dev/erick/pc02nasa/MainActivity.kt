package dev.erick.pc02nasa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.erick.pc02nasa.ui.ApodDetailScreen
import dev.erick.pc02nasa.ui.ApodFavoritesScreen
import dev.erick.pc02nasa.ui.ApodScreen
import dev.erick.pc02nasa.ui.theme.PC02NASATheme
import dev.erick.pc02nasa.viewmodel.ApodViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PC02NASATheme {
                val navController = rememberNavController()
                val apodViewModel: ApodViewModel = viewModel()

                NavHost(
                    navController = navController,
                    startDestination = "apod_list",
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable("apod_list") {
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
                }
            }
        }
    }
}
