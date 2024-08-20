package com.example.recharge.domain.useCase

import com.example.recharge.R
import com.example.recharge.presentation.util.RechargeUiIntent
import com.example.sharedData.model.RechargeModel
import com.example.utils.resourceProvider.ResourceProvider
import com.example.utils.usecases.FlowUseCase
import com.paymob.pos.utils.dispatchers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AmountUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val resourceProvider: ResourceProvider
) : FlowUseCase<Int, RechargeUiIntent>(ioDispatcher) {

     public override suspend fun execute(amount: Int): Flow<RechargeUiIntent> = flow {
        if (amount >= 25){
            emit(RechargeUiIntent(isEnable = true))
        } else
            error(RechargeUiIntent(isEnable = false))
    }


}

