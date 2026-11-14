package dev.erick.pc02nasa.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AsteroidResponseDto(
    @SerializedName("near_earth_objects")
    val nearEarthObjects: Map<String, List<AsteroidDto>>
)

data class AsteroidDto(
    val id: String,
    val name: String,
    @SerializedName("is_potentially_hazardous_asteroid")
    val isPotentiallyHazardous: Boolean,
    @SerializedName("estimated_diameter")
    val estimatedDiameter: EstimatedDiameterDto,
    @SerializedName("close_approach_data")
    val closeApproachData: List<CloseApproachDataDto>
)

data class EstimatedDiameterDto(
    val meters: MetersDto
)

data class MetersDto(
    @SerializedName("estimated_diameter_min")
    val estimatedDiameterMin: Double,
    @SerializedName("estimated_diameter_max")
    val estimatedDiameterMax: Double
)

data class CloseApproachDataDto(
    @SerializedName("close_approach_date")
    val closeApproachDate: String,
    @SerializedName("relative_velocity")
    val relativeVelocity: RelativeVelocityDto,
    @SerializedName("miss_distance")
    val missDistance: MissDistanceDto
)

data class RelativeVelocityDto(
    @SerializedName("kilometers_per_hour")
    val kilometersPerHour: String
)

data class MissDistanceDto(
    val kilometers: String
)
