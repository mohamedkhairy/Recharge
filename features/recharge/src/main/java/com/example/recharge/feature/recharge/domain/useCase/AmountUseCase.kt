package com.example.recharge.feature.recharge.domain.useCase

import com.example.recharge.feature.recharge.presentation.util.RechargeUiIntent
import com.example.utils.usecases.FlowUseCase
import com.example.utils.dispatchers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


/**
 * @param ioDispatcher The [CoroutineDispatcher] used for executing this use case.
 */
open class AmountUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : FlowUseCase<String?, RechargeUiIntent>(ioDispatcher) {


    /**
     * Executes the use case with the provided amount.
     *
     * @param amount A nullable [String] representing the amount in cents.
     * @return A [Flow] emitting a [RechargeUiIntent] indicating if the recharge action is enabled.
     */
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

