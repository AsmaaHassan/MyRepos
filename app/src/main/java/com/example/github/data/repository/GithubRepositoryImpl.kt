package com.example.github.data.repository

import com.example.github.data.datasources.local.AuthStore
import com.example.github.data.datasources.remote.api.GithubApi
import com.example.github.data.mappers.toDomain
import com.example.github.domain.model.Branch
import com.example.github.domain.model.Repo
import com.example.github.domain.repository.GithubRepository

/**
 * Created by AsmaaHassan on 16,November,2025
 * Trufla Technology,
 * Cairo, Egypt.
 */

class GithubRepositoryImpl(
    private val api: GithubApi,
    private val auth: AuthStore
) : GithubRepository {

    override suspend fun exchangeCodeForToken(code: String): String {
        // TODO: Implement actual OAuth token exchange
        val dummyToken = "YOUR_TOKEN"
        auth.saveToken(dummyToken)
        return dummyToken
    }

    override suspend fun getRepos(page: Int): List<Repo> =
        api.getRepos(page).map { it.toDomain() }

    override suspend fun getBranches(owner: String, repo: String, page: Int): List<Branch> =
        api.getBranches(owner, repo, page).map { it.toDomain() }

    override fun logout(): Unit = auth.clear()
}
