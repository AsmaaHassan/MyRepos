package com.example.github.data.remote.dto

import com.squareup.moshi.Json
/**
 * Created by AsmaaHassan on 16,November,2025
 * Trufla Technology,
 * Cairo, Egypt.
 */

data class TokenResponseDto(
    @Json(name = "access_token")
    val accessToken: String?,
    @Json(name = "token_type")
    val tokenType: String?,
    val scope: String?,
    @Json(name = "error")
    val error: String?,
    @Json(name = "error_description")
    val errorDescription: String?
)
