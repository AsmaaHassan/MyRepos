package com.example.github

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.github.presentation.navigation.AppNavHost
import com.example.github.ui.theme.GithubTheme
import dagger.hilt.EntryPoints
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableSharedFlow

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
//    @Inject
//    lateinit var loginViewModelFactory: ViewModelProvider.Factory // optional
    override fun onResume() {
        super.onResume()
    val data: Uri? = intent?.data
    Log.i("Asmaa", "dataa $data")
    if (data != null && data.isHierarchical) {
        val code = data.getQueryParameter("code") // GitHub returns ?code=...
        val error = data.getQueryParameter("error")

        if (code != null) {
            // Use the code to request access token
            Log.d("Auth", "Code: $code")
            if (!code.isNullOrEmpty()) {
                Log.d("Asmaa", "Received code: $code")

                // Send the code to your composable or viewmodel
                OAuthEventBus.authCode.tryEmit(code)
            }
        } else if (error != null) {
            Log.e("Auth", "Error: $error")
        }
    }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val data: Uri? = intent?.data
        if (data != null && data.isHierarchical) {
            val code = data.getQueryParameter("code") // GitHub returns ?code=...
            val error = data.getQueryParameter("error")

            if (code != null) {
                // Use the code to request access token
                Log.d("Auth", "Code: $code")
            } else if (error != null) {
                Log.e("Auth", "Error: $error")
            }
        }
        setContent {
            GithubTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    AppNavHost(navController, this)
                }
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
Log.i("Asmaa", "onActivityResult $requestCode $data")
        super.onActivityResult(requestCode, resultCode, data)
    }



    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.i("Asmaa", "onNewIntent")
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
//        intent?.data?.let { uri ->
//            // expecting redirect like: myapp://callback?code=XXXX&state=YYYY
//            val code = uri.getQueryParameter("code")
//            val state = uri.getQueryParameter("state")
//            if (!code.isNullOrBlank()) {
//                // You can't directly get a ViewModel instance easily here (it's fine to pass to your Nav/compose).
//                // Option A: use an injected AuthHandler to perform exchange:
//                lifecycleScope.launchWhenStarted {
//                    // Resolve UseCases via Hilt to exchange token immediately:
//                    val useCases = EntryPoints.get(applicationContext, UseCasesEntryPoint::class.java).authUseCase()
//                    try {
//                        val token = useCases.login(code, BuildConfig.GITHUB_CLIENT_ID, BuildConfig.GITHUB_CLIENT_SECRET, BuildConfig.GITHUB_REDIRECT_URI)
//                        // success -> navigate to repos screen
//                        // e.g., send a local broadcast, or set a shared StateFlow your Compose observes
//                        // simplest: start the Repos activity / nav
//                        // (Here we just log)
//                        Log.d("MainActivity", "Login token: $token")
//                    } catch (e: Exception) {
//                        Log.e("MainActivity", "Login error", e)
//                    }
//                }
//            }
//        }
        val data = intent?.data ?: return

        if (data.scheme == "myapp" && data.host == "callback") {
            val code = data.getQueryParameter("code")
            val state = data.getQueryParameter("state")

            if (!code.isNullOrEmpty()) {

                // Send the code to your composable or viewmodel
                OAuthEventBus.authCode.tryEmit(code)
            }
        }
    }
    object OAuthEventBus {
        val authCode = MutableSharedFlow<String>(replay = 1)
    }
}
