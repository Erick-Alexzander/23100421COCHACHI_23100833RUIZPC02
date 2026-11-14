package dev.erick.pc02nasa.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val apiService: NasaApiService by lazy {
        Retrofit.Builder()
            .baseUrl(NasaApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NasaApiService::class.java)
    }
}
