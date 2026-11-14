package dev.erick.pc02nasa.presentation.asteroids.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.erick.pc02nasa.data.local.entity.AsteroidEntity
import dev.erick.pc02nasa.presentation.asteroids.AsteroidViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsteroidScreen(
    viewModel: AsteroidViewModel,
    onNavigateToDetail: (String) -> Unit,
    onNavigateToFavorites: () -> Unit
) {
    val asteroids by viewModel.asteroids.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()
    val isHazardousOnly by viewModel.filterHazardous.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Asteroides cercanos") },
                actions = {
                    Button(onClick = onNavigateToFavorites) {
                        Text("Mis asteroides")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Solo potencialmente peligrosos")
                Switch(
                    checked = isHazardousOnly,
                    onCheckedChange = { viewModel.toggleHazardousFilter(it) }
                )
            }

            if (isLoading && asteroids.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (asteroids.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hay asteroides registrados.")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(asteroids) { asteroid ->
                        AsteroidItem(
                            asteroid = asteroid,
                            onClick = { onNavigateToDetail(asteroid.id) },
                            onFavoriteToggle = { 
                                viewModel.toggleFavorite(asteroid.id, !asteroid.isFavorite) 
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AsteroidItem(
    asteroid: AsteroidEntity,
    onClick: () -> Unit,
    onFavoriteToggle: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = asteroid.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = onFavoriteToggle) {
                    Icon(
                        imageVector = if (asteroid.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorito",
                        tint = if (asteroid.isFavorite) Color.Red else Color.Gray
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text("Fecha: ${asteroid.approachDate}")
            Text("Diám. Max: String.format(\"%.2f\", asteroid.maximumDiameterMeters) m".replace("String.format(\"%.2f\", asteroid.maximumDiameterMeters)", String.format("%.2f", asteroid.maximumDiameterMeters)))
            Text("Velocidad: String.format(\"%.2f\", asteroid.velocityKmH) km/h".replace("String.format(\"%.2f\", asteroid.velocityKmH)", String.format("%.2f", asteroid.velocityKmH)))
            Text("Distancia: String.format(\"%.2f\", asteroid.missDistanceKm) km".replace("String.format(\"%.2f\", asteroid.missDistanceKm)", String.format("%.2f", asteroid.missDistanceKm)))
            
            if (asteroid.isPotentiallyHazardous) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "POTENCIALMENTE PELIGROSO",
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
