package com.example.github.domain.model

/**
 * Created by AsmaaHassan on 16,November,2025
 * Trufla Technology,
 * Cairo, Egypt.
 */
data class Repo(val name: String,
                val isPrivate: Boolean,
                val stars: Int,
                val language: String?,
                val updatedAt: String,
                val owner: String)