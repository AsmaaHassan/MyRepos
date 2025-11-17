package com.example.github.domain.usecase

import com.example.github.domain.repository.AuthRepository

/**
 * Created by AsmaaHassan on 16,November,2025
 * Trufla Technology,
 * Cairo, Egypt.
 */
class LoginWithGithubUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(code: String, clientId: String, clientSecret: String, redirectUri: String) =
        authRepository.exchangeCodeForToken(code, clientId, clientSecret, redirectUri)
}