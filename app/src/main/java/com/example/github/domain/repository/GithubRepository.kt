package com.example.github.domain.repository

import com.example.github.domain.model.Branch
import com.example.github.domain.model.Repo

/**
 * Created by AsmaaHassan on 16,November,2025
 * Trufla Technology,
 * Cairo, Egypt.
 */
interface GithubRepository {
    suspend fun exchangeCodeForToken(code: String): String
    suspend fun getRepos(page: Int): List<Repo>
    suspend fun getBranches(owner: String, repo: String, page: Int): List<Branch>
    fun logout()
}