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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import dev.erick.pc02nasa.data.remote.NeoWsNetwork
import dev.erick.pc02nasa.presentation.asteroids.AsteroidViewModel
import dev.erick.pc02nasa.presentation.asteroids.AsteroidViewModelFactory
import dev.erick.pc02nasa.presentation.navigation.AppNavGraph
import dev.erick.pc02nasa.presentation.navigation.BottomNavigationBar
import dev.erick.pc02nasa.ui.theme.PC02NASATheme
import dev.erick.pc02nasa.viewmodel.ApodViewModel

class MainActivity : ComponentActivity() {

    private lateinit var asteroidViewModel: AsteroidViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val application = application as NasaApplication
        val database = application.database
        val api = NeoWsNetwork.neoWsService
        val factory = AsteroidViewModelFactory(api, database.asteroidDao())

        asteroidViewModel = ViewModelProvider(this, factory)[AsteroidViewModel::class.java]

        setContent {
            PC02NASATheme {
                val navController = rememberNavController()
                val apodViewModel: ApodViewModel = viewModel()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavigationBar(navController = navController)
                    }
                ) { innerPadding ->
                    AppNavGraph(
                        navController = navController,
                        asteroidViewModel = asteroidViewModel,
                        apodViewModel = apodViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
