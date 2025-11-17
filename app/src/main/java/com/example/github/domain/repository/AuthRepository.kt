package com.example.github.domain.repository

/**
 * Created by AsmaaHassan on 16,November,2025
 * Trufla Technology,
 * Cairo, Egypt.
 */

interface AuthRepository {
    suspend fun exchangeCodeForToken(code: String, clientId: String, clientSecret: String, redirectUri: String): String
    fun clearToken()
}
