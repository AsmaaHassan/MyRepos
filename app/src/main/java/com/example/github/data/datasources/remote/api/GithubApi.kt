package com.example.github.data.datasources.remote.api

import com.example.github.data.models.dto.BranchDto
import com.example.github.data.models.dto.RepoDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
/**
 * Created by AsmaaHassan on 16,November,2025
 * Trufla Technology,
 * Cairo, Egypt.
 */

interface GithubApi {

    @GET("user/repos")
    suspend fun getRepos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 20
    ): List<RepoDto>

    @GET("repos/{owner}/{repo}/branches")
    suspend fun getBranches(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 20
    ): List<BranchDto>
}