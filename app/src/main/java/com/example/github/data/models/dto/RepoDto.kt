package com.example.github.data.models.dto

import com.squareup.moshi.Json

/**
 * Created by AsmaaHassan on 16,November,2025
 * Trufla Technology,
 * Cairo, Egypt.
 */

data class RepoDto(
    val name: String,
    val private: Boolean,
    @Json(name = "stargazers_count")
    val stars: Int,
    val language: String?,
    @Json(name = "updated_at")
    val updatedAt: String,
    val owner: OwnerDto
)

data class OwnerDto(
    val login: String
)
