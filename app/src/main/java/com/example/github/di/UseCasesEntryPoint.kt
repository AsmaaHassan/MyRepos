package com.example.github.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by AsmaaHassan on 16,November,2025
 * Trufla Technology,
 * Cairo, Egypt.
 */
@EntryPoint
@InstallIn(SingletonComponent::class)
interface UseCasesEntryPoint {
    fun useCases(): UseCases
    fun authUseCase(): AuthUseCases
}