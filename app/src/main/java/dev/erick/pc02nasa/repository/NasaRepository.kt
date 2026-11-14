package dev.erick.pc02nasa.repository

import android.content.Context
import dev.erick.pc02nasa.BuildConfig
import dev.erick.pc02nasa.data.NasaApiService
import dev.erick.pc02nasa.data.RetrofitClient
import dev.erick.pc02nasa.data.local.NasaDatabase
import dev.erick.pc02nasa.data.local.dao.ApodDao
import dev.erick.pc02nasa.data.local.entity.ApodEntity
import dev.erick.pc02nasa.model.ApodItem
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NasaRepository(
    private val apiService: NasaApiService = RetrofitClient.apiService,
    context: Context? = null
) {
    private val apodDao: ApodDao? = context?.let { NasaDatabase.getDatabase(it).apodDao() }

    suspend fun getLast7DaysApod(): List<ApodItem> {
        val endDate = LocalDate.now()
        val startDate = endDate.minusDays(6)
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE
        
        val startStr = startDate.format(formatter)
        val endStr = endDate.format(formatter)

        return apiService.getApodList(
            apiKey = BuildConfig.NASA_API_KEY,
            startDate = startStr,
            endDate = endStr
        )
    }

    val favoriteApods: Flow<List<ApodEntity>>? = apodDao?.getAllApods()

    suspend fun insertFavorite(apod: ApodEntity) {
        apodDao?.insertApod(apod)
    }

    suspend fun removeFavorite(apod: ApodEntity) {
        apodDao?.deleteApod(apod)
    }

    suspend fun getFavoriteByDate(date: String): ApodEntity? {
        return apodDao?.getApodByDate(date)
    }
}
