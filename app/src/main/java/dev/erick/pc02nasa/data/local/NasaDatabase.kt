package dev.erick.pc02nasa.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.erick.pc02nasa.data.local.dao.ApodDao
import dev.erick.pc02nasa.data.local.dao.AsteroidDao
import dev.erick.pc02nasa.data.local.entity.ApodEntity
import dev.erick.pc02nasa.data.local.entity.AsteroidEntity

@Database(
    entities = [AsteroidEntity::class, ApodEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NasaDatabase : RoomDatabase() {

    abstract fun asteroidDao(): AsteroidDao
    abstract fun apodDao(): ApodDao

    companion object {
        @Volatile
        private var INSTANCE: NasaDatabase? = null

        fun getDatabase(context: Context): NasaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NasaDatabase::class.java,
                    "nasa_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
