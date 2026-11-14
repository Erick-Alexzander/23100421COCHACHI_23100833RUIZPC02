package dev.erick.pc02nasa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import dev.erick.pc02nasa.data.remote.NeoWsNetwork
import dev.erick.pc02nasa.presentation.asteroids.AsteroidViewModel
import dev.erick.pc02nasa.presentation.asteroids.AsteroidViewModelFactory
import dev.erick.pc02nasa.presentation.navigation.AppNavGraph
import dev.erick.pc02nasa.presentation.navigation.BottomNavigationBar
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

class MainActivity : ComponentActivity() {

    private lateinit var asteroidViewModel: AsteroidViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicialización manual de dependencias
        val application = application as NasaApplication
        val database = application.database
        val api = NeoWsNetwork.neoWsService
        val factory = AsteroidViewModelFactory(api, database.asteroidDao())
        
        asteroidViewModel = ViewModelProvider(this, factory)[AsteroidViewModel::class.java]

        setContent {
            PC02NASATheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavigationBar(navController = navController)
                    }
                ) { innerPadding ->
                    AppNavGraph(
                        navController = navController,
                        asteroidViewModel = asteroidViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
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
