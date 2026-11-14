package dev.erick.pc02nasa.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "asteroids")
data class AsteroidEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val approachDate: String,
    val minimumDiameterMeters: Double,
    val maximumDiameterMeters: Double,
    val velocityKmH: Double,
    val missDistanceKm: Double,
    val isPotentiallyHazardous: Boolean,
    val isFavorite: Boolean = false
)
