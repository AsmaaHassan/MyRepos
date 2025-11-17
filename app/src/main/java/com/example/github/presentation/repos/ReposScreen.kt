package com.example.github.presentation.repos

import android.graphics.Color
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.github.domain.model.Repo
import com.example.github.presentation.compose_components.SharedComposables.RepoItem

/**
 * Created by AsmaaHassan on 16,November,2025
 * Trufla Technology,
 * Cairo, Egypt.
 */



@Composable
fun ReposScreen(viewModel: ReposViewModel,navController: NavController) {
    val state = viewModel.state

    LaunchedEffect(Unit) { viewModel.loadRepos() }
    Box(modifier=Modifier.padding(30.dp)) {
    when(state) {
        ReposUiState.Loading -> CircularProgressIndicator()
        ReposUiState.Error-> Text("Something went wrong")
        is ReposUiState.Success -> {

            val data = state.data
            LazyColumn {
                items(data.size) { i ->
                    val repo = data[i]
                    RepoItem(repo = repo, viewModel = viewModel, navController = navController)
                }
            }
        }
        }
    }
}
