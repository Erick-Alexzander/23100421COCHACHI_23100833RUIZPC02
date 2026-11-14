package dev.erick.pc02nasa.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.erick.pc02nasa.data.local.entity.ApodEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ApodDao {

    @Query("SELECT * FROM apod_images ORDER BY date DESC")
    fun getAllApods(): Flow<List<ApodEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApod(apod: ApodEntity)

    @Delete
    suspend fun deleteApod(apod: ApodEntity)

    @Query("SELECT * FROM apod_images WHERE date = :date")
    suspend fun getApodByDate(date: String): ApodEntity?
}
