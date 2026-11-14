package dev.erick.pc02nasa.presentation.asteroids.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.erick.pc02nasa.presentation.asteroids.AsteroidViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsteroidDetailScreen(
    asteroidId: String,
    viewModel: AsteroidViewModel,
    onNavigateBack: () -> Unit
) {
    val asteroid by viewModel.getAsteroidById(asteroidId).collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Asteroide") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            if (asteroid == null) {
                CircularProgressIndicator()
            } else {
                val data = asteroid!!
                Text(text = data.name, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(text = "ID: ${data.id}")
                Text(text = "Fecha de aproximación: ${data.approachDate}")
                Text(text = "Diámetro Mín: ${"%.2f".format(java.util.Locale.US, data.minimumDiameterMeters)} m")
                Text(text = "Diámetro Máx: ${"%.2f".format(java.util.Locale.US, data.maximumDiameterMeters)} m")
                Text(text = "Velocidad Relativa: ${"%.2f".format(java.util.Locale.US, data.velocityKmH)} km/h")
                Text(text = "Distancia de paso: ${"%.2f".format(java.util.Locale.US, data.missDistanceKm)} km")

                Spacer(modifier = Modifier.height(16.dp))
                
                if (data.isPotentiallyHazardous) {
                    Text(text = "¡POTENCIALMENTE PELIGROSO!", color = Color.Red, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Button(
                    onClick = { viewModel.toggleFavorite(data.id, !data.isFavorite) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (data.isFavorite) "Quitar de favoritos" else "Añadir a favoritos")
                }
            }
        }
    }
}
