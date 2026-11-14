package dev.erick.pc02nasa.data.mapper

import dev.erick.pc02nasa.data.local.entity.AsteroidEntity
import dev.erick.pc02nasa.data.remote.dto.AsteroidDto

fun AsteroidDto.toEntity(): AsteroidEntity? {
    // Tomamos la primera aproximación registrada. Como la API nos devuelve agrupado
    // por fecha de la consulta, la primera de la lista suele ser la correspondiente a esa fecha.
    val approach = closeApproachData.firstOrNull() ?: return null
    
    val velocity = approach.relativeVelocity.kilometersPerHour.toDoubleOrNull() ?: 0.0
    val missDist = approach.missDistance.kilometers.toDoubleOrNull() ?: 0.0

    return AsteroidEntity(
        id = id,
        name = name,
        approachDate = approach.closeApproachDate,
        minimumDiameterMeters = estimatedDiameter.meters.estimatedDiameterMin,
        maximumDiameterMeters = estimatedDiameter.meters.estimatedDiameterMax,
        velocityKmH = velocity,
        missDistanceKm = missDist,
        isPotentiallyHazardous = isPotentiallyHazardous,
        isFavorite = false // Por defecto es false, el DAO conserva el favorito existente si ya hay uno.
    )
}
