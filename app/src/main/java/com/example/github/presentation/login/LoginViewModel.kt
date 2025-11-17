package com.example.github.presentation.login


/**
 * Created by AsmaaHassan on 16,November,2025
 * Trufla Technology,
 * Cairo, Egypt.
 */


import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.github.BuildConfig
import com.example.github.di.AuthUseCases
import com.example.github.di.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCases: AuthUseCases
) : ViewModel() {

    val isLoading = mutableStateOf(false)
    val error = mutableStateOf<String?>(null)

    // Build the GitHub authorize URL
    fun buildAuthUrl(clientId: String, redirectUri: String, state: String, scope: String = "repo read:user"): String {
        val encodedRedirect = Uri.encode(redirectUri)
        val x= "https://github.com/login/oauth/authorize" +
                "?client_id=$clientId" +
                "&redirect_uri=$encodedRedirect" +
                "&scope=${Uri.encode(scope)}" +
                "&state=$state"
        Log.i("Asmaa", "url $x")
        return x
    }

    // Exchanges code -> token using provided UseCase (saves token in AuthStore)
    fun handleCode(code: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading.value = true
            error.value = null
            try {
                // Supply client id/secret/redirectUri from BuildConfig
                val token = useCases.login(code, BuildConfig.GITHUB_CLIENT_ID, BuildConfig.GITHUB_CLIENT_SECRET, BuildConfig.GITHUB_REDIRECT_URI)
                // token saved inside repository via AuthStore
                onSuccess()
            } catch (e: Exception) {
                error.value = e.message ?: "Unknown error"
            } finally {
                isLoading.value = false
            }
        }
    }
}
