package dev.erick.pc02nasa.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.erick.pc02nasa.data.local.entity.ApodEntity
import kotlinx.coroutines.flow.Flow

/**
 * Contrato de integración para el módulo APOD (Kiara).
 * Kiara: Agrega aquí tus consultas específicas para el diario APOD.
 */
@Dao
interface ApodDao {

    @Query("SELECT * FROM apod_images ORDER BY date DESC")
    fun getAllApods(): Flow<List<ApodEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApod(apod: ApodEntity)

    // Agrega los métodos de actualización o borrado si los necesitas
}
