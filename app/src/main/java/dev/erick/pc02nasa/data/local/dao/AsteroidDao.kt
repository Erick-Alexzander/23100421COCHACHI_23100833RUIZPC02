package dev.erick.pc02nasa.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.erick.pc02nasa.data.local.entity.AsteroidEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AsteroidDao {

    @Query("SELECT * FROM asteroids ORDER BY approachDate DESC")
    fun getAllAsteroids(): Flow<List<AsteroidEntity>>

    @Query("SELECT * FROM asteroids WHERE id = :id")
    fun getAsteroidById(id: String): Flow<AsteroidEntity?>

    @Query("SELECT * FROM asteroids WHERE id = :id")
    suspend fun getAsteroidByIdSync(id: String): AsteroidEntity?

    @Query("SELECT * FROM asteroids WHERE isFavorite = 1 ORDER BY approachDate DESC")
    fun getFavoriteAsteroids(): Flow<List<AsteroidEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAsteroid(asteroid: AsteroidEntity)

    @Update
    suspend fun updateAsteroid(asteroid: AsteroidEntity)

    @Query("UPDATE asteroids SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteStatus(id: String, isFavorite: Boolean)
}
