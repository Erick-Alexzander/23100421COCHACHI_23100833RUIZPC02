package dev.erick.pc02nasa.presentation.asteroids.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.erick.pc02nasa.presentation.asteroids.AsteroidViewModel
import dev.erick.pc02nasa.presentation.asteroids.list.AsteroidItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsteroidFavoritesScreen(
    viewModel: AsteroidViewModel,
    onNavigateToDetail: (String) -> Unit,
    onNavigateBack: () -> Unit
) {
    val favorites by viewModel.favoriteAsteroids.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis asteroides") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        if (favorites.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No tienes asteroides favoritos.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(favorites) { asteroid ->
                    AsteroidItem(
                        asteroid = asteroid,
                        onClick = { onNavigateToDetail(asteroid.id) },
                        onFavoriteToggle = {
                            viewModel.toggleFavorite(asteroid.id, false)
                        }
                    )
                }
            }
        }
    }
}
