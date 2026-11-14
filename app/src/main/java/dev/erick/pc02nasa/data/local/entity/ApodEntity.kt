package dev.erick.pc02nasa.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad Room para el módulo APOD (Kiara).
 * Tabla: "apod_images"
 */
@Entity(tableName = "apod_images")
data class ApodEntity(
    @PrimaryKey
    val date: String,
    val title: String?,
    val url: String?,
    val explanation: String?,
    val hdurl: String?,
    val mediaType: String?,
    val copyright: String?
)
