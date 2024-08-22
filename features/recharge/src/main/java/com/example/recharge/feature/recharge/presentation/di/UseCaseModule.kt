package com.example.recharge.feature.recharge.presentation.di

import com.example.recharge.feature.recharge.domain.useCase.AmountUseCase
import com.example.recharge.feature.recharge.domain.useCase.CodeUseCase
import com.example.recharge.feature.recharge.presentation.util.RechargeUiIntent
import com.example.utils.usecases.FlowUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {


    @Singleton
    @Binds
    abstract fun bindAmountUseCase(amountUseCase: AmountUseCase): FlowUseCase<String?, RechargeUiIntent>

    @Singleton
    @Binds
    abstract fun bindCodeUseCase(codeUseCase: CodeUseCase): FlowUseCase<String?, RechargeUiIntent>


}