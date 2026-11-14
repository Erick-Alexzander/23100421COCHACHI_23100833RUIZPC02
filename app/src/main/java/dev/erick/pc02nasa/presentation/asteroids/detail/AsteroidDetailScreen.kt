package dev.erick.pc02nasa.presentation.asteroids.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver")
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
                Text(text = "Diámetro Mín: String.format(\"%.2f\", data.minimumDiameterMeters) m".replace("String.format(\"%.2f\", data.minimumDiameterMeters)", String.format("%.2f", data.minimumDiameterMeters)))
                Text(text = "Diámetro Máx: String.format(\"%.2f\", data.maximumDiameterMeters) m".replace("String.format(\"%.2f\", data.maximumDiameterMeters)", String.format("%.2f", data.maximumDiameterMeters)))
                Text(text = "Velocidad Relativa: String.format(\"%.2f\", data.velocityKmH) km/h".replace("String.format(\"%.2f\", data.velocityKmH)", String.format("%.2f", data.velocityKmH)))
                Text(text = "Distancia de paso: String.format(\"%.2f\", data.missDistanceKm) km".replace("String.format(\"%.2f\", data.missDistanceKm)", String.format("%.2f", data.missDistanceKm)))

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
