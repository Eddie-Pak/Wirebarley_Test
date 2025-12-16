package com.wirebarley.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

sealed interface ExchangeApiResponse

@JsonClass(generateAdapter = true)
data class ExchangeSuccessResponse(
    @Json(name = "success") val success: Boolean,
    @Json(name = "terms") val terms: String,
    @Json(name = "privacy") val privacy: String,
    @Json(name = "timestamp") val timestamp: Long,
    @Json(name = "source") val source: String,
    @Json(name = "quotes") val quotes: Map<String, Double>
) : ExchangeApiResponse

@JsonClass(generateAdapter = true)
data class ExchangeErrorResponse(
    @Json(name = "success") val success: Boolean,
    @Json(name = "error") val error: ErrorDetail
) : ExchangeApiResponse

@JsonClass(generateAdapter = true)
data class ErrorDetail(
    @Json(name = "code") val code: Int,
    @Json(name = "type") val type: String,
    @Json(name = "info") val info: String
)