package dev.erick.pc02nasa.repository

import dev.erick.pc02nasa.BuildConfig
import dev.erick.pc02nasa.data.NasaApiService
import dev.erick.pc02nasa.data.RetrofitClient
import dev.erick.pc02nasa.model.ApodItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NasaRepository(
    private val apiService: NasaApiService = RetrofitClient.apiService
) {
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
}
