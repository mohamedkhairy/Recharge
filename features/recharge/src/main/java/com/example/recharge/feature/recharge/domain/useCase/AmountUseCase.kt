package com.example.recharge.feature.recharge.domain.useCase

import com.example.recharge.feature.recharge.presentation.util.RechargeUiIntent
import com.example.utils.usecases.FlowUseCase
import com.example.utils.dispatchers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

open class AmountUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : FlowUseCase<String?, RechargeUiIntent>(ioDispatcher) {

     public override suspend fun execute(amount: String?): Flow<RechargeUiIntent> = flow {
         amount?.let{
             val amountInCent = amount.toInt()
             if (amountInCent >= 25){
                 emit(RechargeUiIntent(isEnable = true))
             } else
                 emit(RechargeUiIntent(isEnable = false))
         } ?: run { error("Invalid Amount") }
    }

}

