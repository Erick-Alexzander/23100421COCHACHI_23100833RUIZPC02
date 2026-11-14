package dev.erick.pc02nasa.data.repository

import dev.erick.pc02nasa.BuildConfig
import dev.erick.pc02nasa.data.local.dao.AsteroidDao
import dev.erick.pc02nasa.data.local.entity.AsteroidEntity
import dev.erick.pc02nasa.data.mapper.toEntity
import dev.erick.pc02nasa.data.remote.NeoWsService
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AsteroidRepository(
    private val api: NeoWsService,
    private val dao: AsteroidDao
) {
    fun getAllAsteroids(): Flow<List<AsteroidEntity>> {
        return dao.getAllAsteroids()
    }

    fun getFavoriteAsteroids(): Flow<List<AsteroidEntity>> {
        return dao.getFavoriteAsteroids()
    }

    fun getAsteroidById(id: String): Flow<AsteroidEntity?> {
        return dao.getAsteroidById(id)
    }

    suspend fun updateFavoriteStatus(id: String, isFavorite: Boolean) {
        dao.updateFavoriteStatus(id, isFavorite)
    }

    suspend fun syncAsteroids() {
        try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val endDate = LocalDate.now()
            val startDate = endDate.minusDays(6)

            val startDateStr = startDate.format(formatter)
            val endDateStr = endDate.format(formatter)
            val apiKey = BuildConfig.NASA_API_KEY

            val response = api.getAsteroids(startDateStr, endDateStr, apiKey)
            
            // Aplanar el mapa de fechas a una sola lista y convertir a entities
            val entities = response.nearEarthObjects.values
                .flatten()
                .mapNotNull { it.toEntity() }

            if (entities.isNotEmpty()) {
                dao.insertOrUpdateAsteroids(entities)
            }
        } catch (e: Exception) {
            // Manejar errores de red u otros sin romper la aplicación
            // En un caso real, podríamos devolver un Result con el error
            e.printStackTrace()
            throw e
        }
    }
}
