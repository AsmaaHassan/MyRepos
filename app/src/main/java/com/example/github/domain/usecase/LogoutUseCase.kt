package com.example.github.domain.usecase

import com.example.github.domain.repository.GithubRepository

/**
 * Created by AsmaaHassan on 16,November,2025
 * Trufla Technology,
 * Cairo, Egypt.
 */
class LogoutUseCase(private val repository: GithubRepository) {
    operator fun invoke() = repository.logout()
}