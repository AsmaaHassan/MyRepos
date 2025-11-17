package com.example.github.presentation.repodetails

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.github.presentation.compose_components.SharedComposables.BranchItem
import com.example.github.presentation.compose_components.SharedComposables.RepoItem
import com.example.github.presentation.repos.ReposViewModel

/**
 * Created by AsmaaHassan on 17,November,2025
 * Trufla Technology,
 * Cairo, Egypt.
 */

@Composable
fun RepoDetails(viewModel: ReposViewModel,navController: NavHostController) {

    Column(modifier=Modifier.padding(30.dp)) {
        viewModel.selectedRepo?.let {
            RepoItem(
                false,
                it,
                viewModel = viewModel,
                navController = navController
            )
            LaunchedEffect(Unit) { viewModel.loadBranches() }

            Spacer(Modifier.height(5.dp))

            Text(text = "Branches")
            Divider(Modifier.height(1.dp))
            val stateBranches = viewModel.stateBranches

            LaunchedEffect(Unit) { viewModel.loadRepos() }

            when (stateBranches) {
                BranchesUIState.Loading -> CircularProgressIndicator()
                BranchesUIState.Error -> Text("Something went wrong")
                is BranchesUIState.Success -> {
                    Box(modifier = Modifier.padding(20.dp)) {
                        val data = stateBranches.data
                        LazyColumn {
                            items(data.size) { i ->
                                val branch = data[i]
                                BranchItem(branch = branch)
                            }
                        }
                    }
                }
            }


        }
    }




}