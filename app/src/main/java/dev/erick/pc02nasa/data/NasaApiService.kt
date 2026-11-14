package dev.erick.pc02nasa.data

import dev.erick.pc02nasa.model.ApodItem
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApiService {
    @GET("planetary/apod")
    suspend fun getApodList(
        @Query("api_key") apiKey: String,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String
    ): List<ApodItem>

    companion object {
        const val BASE_URL = "https://api.nasa.gov/"
    }
}
