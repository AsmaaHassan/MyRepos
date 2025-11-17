package com.example.github.data.repository

/**
 * Created by AsmaaHassan on 16,November,2025
 * Trufla Technology,
 * Cairo, Egypt.
 */

import com.example.github.data.local.AuthStore
import com.example.github.data.remote.api.TokenApi
import com.example.github.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val tokenApi: TokenApi,
    private val authStore: AuthStore
) : AuthRepository {

    override suspend fun exchangeCodeForToken(code: String, clientId: String, clientSecret: String, redirectUri: String): String {
        val response = tokenApi.exchangeCode(clientId, clientSecret, code, redirectUri)
        val token = response.accessToken ?: throw IllegalStateException("Token response error: ${response.errorDescription ?: response.error}")
        authStore.saveToken(token)
        return token
    }

    override fun clearToken() = authStore.clear()
}
