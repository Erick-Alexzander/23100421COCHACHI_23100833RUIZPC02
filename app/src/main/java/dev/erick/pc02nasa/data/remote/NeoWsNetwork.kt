package dev.erick.pc02nasa.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Configuración mínima solicitada para NeoWs
object NeoWsNetwork {
    private const val BASE_URL = "https://api.nasa.gov/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val neoWsService: NeoWsService by lazy {
        retrofit.create(NeoWsService::class.java)
    }
}

interface NeoWsService {
    @GET("neo/rest/v1/feed")
    suspend fun getAsteroids(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String
    ): dev.erick.pc02nasa.data.remote.dto.AsteroidResponseDto
}
