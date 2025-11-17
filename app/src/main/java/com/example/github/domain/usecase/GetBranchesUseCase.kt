package com.example.github.domain.usecase

import com.example.github.domain.repository.GithubRepository

/**
 * Created by AsmaaHassan on 16,November,2025
 * Trufla Technology,
 * Cairo, Egypt.
 */
class GetBranchesUseCase(private val repository: GithubRepository) {
    suspend operator fun invoke(owner: String, repo: String, page: Int) =
        repository.getBranches(owner, repo, page)
}