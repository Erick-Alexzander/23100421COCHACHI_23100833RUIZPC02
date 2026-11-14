package dev.erick.pc02nasa.model

import com.google.gson.annotations.SerializedName

data class ApodItem(
    @SerializedName("title")
    val title: String?,
    @SerializedName("date")
    val date: String?,
    @SerializedName("explanation")
    val explanation: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("hdurl")
    val hdurl: String?,
    @SerializedName("media_type")
    val mediaType: String?,
    @SerializedName("service_version")
    val serviceVersion: String?,
    @SerializedName("copyright")
    val copyright: String?
)
