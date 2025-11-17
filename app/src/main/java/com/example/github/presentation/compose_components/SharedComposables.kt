package com.example.github.presentation.compose_components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.github.domain.model.Branch
import com.example.github.domain.model.Repo
import com.example.github.presentation.repos.ReposViewModel

/**
 * Created by AsmaaHassan on 17,November,2025
 * Trufla Technology,
 * Cairo, Egypt.
 */
object SharedComposables{
    @Composable fun RepoItem(isClickable: Boolean=true, repo: Repo, viewModel: ReposViewModel, navController: NavController){
        Row(horizontalArrangement = Arrangement.SpaceBetween,modifier = Modifier.padding(bottom =10.dp)
            .clickable(isClickable, onClick = { onclickRepo(repo,viewModel,navController) })) {
            Text(repo.name, color = androidx.compose.ui.graphics.Color.Black, modifier = Modifier.padding(end =5.dp))
            Text(    text = if (repo.isPrivate) "Private" else "Public", color = androidx.compose.ui.graphics.Color.Black, modifier = Modifier.padding(end =5.dp))
            Text(repo.stars.toString(), color = androidx.compose.ui.graphics.Color.Black)
//        Text(repo.updatedAt.toString(), color = androidx.compose.ui.graphics.Color.Black)
        }
    }

    fun onclickRepo(repo: Repo,viewModel: ReposViewModel,navController: NavController){
        Log.i("Asmaa", "onClickRepo $repo" )
        viewModel.selectRepo(repo)   // store object
        navController.navigate("reposdetails")


    }


    @Composable fun BranchItem(branch: Branch){
        Row(horizontalArrangement = Arrangement.SpaceBetween,modifier = Modifier.padding(bottom =10.dp)) {
            Text(branch.name, color = androidx.compose.ui.graphics.Color.Black, modifier = Modifier.padding(end =5.dp))
        }
    }
}