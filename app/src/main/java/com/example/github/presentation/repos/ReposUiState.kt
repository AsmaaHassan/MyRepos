package com.example.github.presentation.repos

import com.example.github.domain.model.Repo
/**
 * Created by AsmaaHassan on 16,November,2025
 * Trufla Technology,
 * Cairo, Egypt.
 */


sealed interface ReposUiState {
    data class Success(val data: List<Repo>) : ReposUiState

    object Loading : ReposUiState
//    val repos: List<Repo> = emptyList(),
object Error : ReposUiState


}
