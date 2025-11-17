package com.example.github.presentation.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.github.presentation.login.LoginScreen
import com.example.github.presentation.login.LoginViewModel
import com.example.github.presentation.repodetails.RepoDetails
import com.example.github.presentation.repos.ReposScreen
import com.example.github.presentation.repos.ReposViewModel

/**
 * Created by AsmaaHassan on 16,November,2025
 * Trufla Technology,
 * Cairo, Egypt.
 */


@Composable
fun AppNavHost(navController: NavHostController, context: Context) {
//    val context = LocalContext.current
    NavHost(navController, startDestination = "login") {

        composable("login") {
            val viewModel: LoginViewModel = hiltViewModel()

            LoginScreen(
                onLoggedIn = {
                    // go to the nested repos graph
                    navController.navigate("repos_graph") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                viewModel = viewModel,
                context = context
            )
        }

        // parent graph for repos-related screens
        navigation(
            startDestination = "repos",
            route = "repos_graph"
        ) {

            composable("repos") { backStackEntry ->
                // get parent entry of repos_graph
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("repos_graph")
                }
                val reposViewModel: ReposViewModel = hiltViewModel(parentEntry)

                ReposScreen(
                    navController = navController,
                    viewModel = reposViewModel
                )
            }

            composable("reposdetails") { backStackEntry ->
                // use the SAME parent entry
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("repos_graph")
                }
                val reposViewModel: ReposViewModel = hiltViewModel(parentEntry)

                RepoDetails(
                    navController = navController,
                    viewModel = reposViewModel
                )
            }
        }
    }
}
