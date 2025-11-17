package com.example.github.presentation.repos

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.github.di.UseCases
import com.example.github.domain.model.Repo
import com.example.github.presentation.repodetails.BranchesUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
/**
 * Created by AsmaaHassan on 16,November,2025
 * Trufla Technology,
 * Cairo, Egypt.
 */



@HiltViewModel
class ReposViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {


    var state : ReposUiState by mutableStateOf(ReposUiState.Loading)
        private set

    var stateBranches : BranchesUIState by mutableStateOf(BranchesUIState.Loading)
        private set
    var selectedRepo: Repo? by mutableStateOf(null)

    fun selectRepo(repo: Repo) {
        selectedRepo = repo
        Log.i("Asmaa", "selected $selectedRepo")
    }
    fun loadRepos() {
        viewModelScope.launch {
            state = ReposUiState.Loading
            runCatching { useCases.getRepos(1) }
                .onSuccess { repos ->

                    state = ReposUiState.Success(repos)
                }
                .onFailure { e ->
                    state = ReposUiState.Error
                }
        }
    }

    fun loadBranches()
    {
        viewModelScope.launch {
            stateBranches = BranchesUIState.Loading
            if (selectedRepo!= null) {
                runCatching { useCases.getBranches(selectedRepo!!.owner, selectedRepo!!.name, 1) }
                    .onSuccess { branches ->

                        stateBranches = BranchesUIState.Success(branches)
                    }
                    .onFailure { e ->
                        stateBranches = BranchesUIState.Error
                    }
            }
        }
    }}
