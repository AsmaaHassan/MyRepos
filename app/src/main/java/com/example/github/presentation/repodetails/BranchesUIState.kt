package com.example.github.presentation.repodetails

import com.example.github.domain.model.Branch
import com.example.github.domain.model.Repo
import com.example.github.presentation.repos.ReposUiState

/**
 * Created by AsmaaHassan on 17,November,2025
 * Trufla Technology,
 * Cairo, Egypt.
 */
sealed interface BranchesUIState {
    data class Success(val data: List<Branch>) : BranchesUIState

    object Loading : BranchesUIState
    //    val repos: List<Repo> = emptyList(),
    object Error : BranchesUIState


}