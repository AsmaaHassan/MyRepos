package com.example.github.di

import com.example.github.data.local.AuthStore
import com.example.github.data.remote.api.GithubApi
import com.example.github.data.remote.api.TokenApi
import com.example.github.data.repository.AuthRepositoryImpl
import com.example.github.data.repository.GithubRepositoryImpl
import com.example.github.domain.repository.AuthRepository
import com.example.github.domain.repository.GithubRepository
import com.example.github.domain.usecase.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by AsmaaHassan on 16,November,2025
 * Trufla Technology,
 * Cairo, Egypt.
 */


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides @Singleton
    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

//    @Provides @Singleton
//    fun provideOkHttp(logging: HttpLoggingInterceptor) =
//        OkHttpClient.Builder().addInterceptor(logging).build()

    @Provides @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides @Singleton
    fun provideGithubApi(okHttp: OkHttpClient, moshi: Moshi): GithubApi =
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttp)
            .build()
            .create(GithubApi::class.java)

    @Provides @Singleton
    fun provideRepository(api: GithubApi, authStore: AuthStore): GithubRepository =
        GithubRepositoryImpl(api, authStore)

    @Provides @Singleton
    fun provideUseCases(repo: GithubRepository) = UseCases(
        getRepos = GetReposUseCase(repo),
        getBranches = GetBranchesUseCase(repo),
        logout = LogoutUseCase(repo)
    )
    @Provides @Singleton
    fun provideAuthUseCases(repo: AuthRepository) = AuthUseCases(
        login = LoginWithGithubUseCase(repo),

    )
    @Provides
    @Singleton
    fun provideOkHttp(logging: HttpLoggingInterceptor,    authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("Accept", "application/json")
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(authInterceptor)
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttp: OkHttpClient, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://github.com/")
            .client(okHttp)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    @Provides
    @Singleton
    fun provideTokenApi(retrofit: Retrofit): TokenApi =
        retrofit.create(TokenApi::class.java)

    @Provides
    @Singleton
    fun provideAuthRepository(api: TokenApi, authStore: AuthStore): AuthRepository =
        AuthRepositoryImpl(api, authStore)


}

data class UseCases(
//    val login: LoginWithGithubUseCase,
    val getRepos: GetReposUseCase,
    val getBranches: GetBranchesUseCase,
    val logout: LogoutUseCase
)
data class AuthUseCases(
    val login: LoginWithGithubUseCase
)


class AuthInterceptor @Inject constructor(private val authStore: AuthStore) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = authStore.getToken()
        val request = chain.request().newBuilder()
            .apply {
                if (!token.isNullOrEmpty()) header("Authorization", "Bearer $token")
            }
            .build()
        return chain.proceed(request)
    }
}