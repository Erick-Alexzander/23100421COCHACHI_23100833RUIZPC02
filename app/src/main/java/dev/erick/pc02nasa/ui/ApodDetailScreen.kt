package dev.erick.pc02nasa.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.erick.pc02nasa.viewmodel.ApodViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApodDetailScreen(
    date: String,
    viewModel: ApodViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val item = viewModel.getApodByDate(date)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(item?.title ?: "Detalle APOD") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                },
                actions = {
                    if (item != null) {
                        val isFav = viewModel.isFavorite(item.date ?: "")
                        IconButton(onClick = { viewModel.toggleFavorite(item) }) {
                            Icon(
                                imageVector = if (isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favorito",
                                tint = if (isFav) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        if (item == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No se encontró información para la fecha: $date",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (item.mediaType == "video") {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp)
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Video",
                                modifier = Modifier.size(80.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Contenido multimedia en Video",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                } else {
                    AsyncImage(
                        model = item.hdurl ?: item.url,
                        contentDescription = item.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(320.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                Text(
                    text = item.title ?: "Sin título",
                    style = MaterialTheme.typography.headlineSmall
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Fecha: ${item.date ?: "N/D"}",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    if (!item.copyright.isNullOrBlank()) {
                        Text(
                            text = "Créditos: ${item.copyright}",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }

                HorizontalDivider()

                Text(
                    text = item.explanation ?: "Sin explicación disponible.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
