package com.wirebarley.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ExchangeApiResponse(
    @Json(name = "success") val success: Boolean,

    @Json(name = "terms") val terms: String? = null,
    @Json(name = "privacy") val privacy: String? = null,
    @Json(name = "timestamp") val timestamp: Long? = null,
    @Json(name = "source") val source: String? = null,
    @Json(name = "quotes") val quotes: Map<String, Double>? = null,

    @Json(name = "error") val error: ErrorDetail? = null
)

@JsonClass(generateAdapter = true)
data class ErrorDetail(
    @Json(name = "code") val code: Int,
    @Json(name = "type") val type: String,
    @Json(name = "info") val info: String
)