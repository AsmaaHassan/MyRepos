package com.example.github.presentation.login

/**
 * Created by AsmaaHassan on 16,November,2025
 * Trufla Technology,
 * Cairo, Egypt.
 */

import android.app.Activity
import android.content.Context
import androidx.browser.customtabs.CustomTabsIntent
//import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.github.BuildConfig
import com.example.github.MainActivity

//import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoggedIn: () -> Unit,
    context: Context
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        MainActivity.OAuthEventBus.authCode.collect { code ->
            viewModel.handleCode(code) {
                onLoggedIn()
            }
        }
    }
    val isLoading by viewModel.isLoading
    val error by viewModel.error

    Column(modifier = Modifier.fillMaxSize().padding(40.dp)) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Button(onClick = {
                // generate state (CSRF token)
                val state = java.util.UUID.randomUUID().toString()
                val authUrl = viewModel.buildAuthUrl(
                    BuildConfig.GITHUB_CLIENT_ID,
                    BuildConfig.GITHUB_REDIRECT_URI,
                    state
                )
                // Open Custom Tabs
                val customTabsIntent = CustomTabsIntent.Builder().build()
                customTabsIntent.launchUrl(context, android.net.Uri.parse(authUrl))
            }) {
                Text("Sign in with GitHub")
            }

            Spacer(modifier = Modifier.height(12.dp))
            error?.let {
                Text("Error: $it")
            }
        }
    }
}
