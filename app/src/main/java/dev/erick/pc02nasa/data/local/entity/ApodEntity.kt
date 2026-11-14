package dev.erick.pc02nasa.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Contrato de integración para el módulo APOD (Kiara).
 * Kiara: Debes definir las columnas necesarias para la imagen APOD.
 * Asegúrate de mantener el nombre de la tabla como "apod_images".
 */
@Entity(tableName = "apod_images")
data class ApodEntity(
    @PrimaryKey
    val date: String, // Asumiendo que la fecha es el ID único. Cambia si es necesario.
    val title: String,
    val url: String,
    val explanation: String
    // Agrega más campos según tu modelo de red (mediaType, etc.)
)
