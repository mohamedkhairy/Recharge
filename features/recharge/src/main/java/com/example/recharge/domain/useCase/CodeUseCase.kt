package com.example.recharge.domain.useCase

import androidx.compose.ui.graphics.Color
import com.example.recharge.presentation.util.RechargeUiIntent
import com.example.utils.resourceProvider.ResourceProvider
import com.example.utils.usecases.FlowUseCase
import com.paymob.pos.utils.dispatchers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CodeUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val resourceProvider: ResourceProvider
) : FlowUseCase<String?, RechargeUiIntent>(ioDispatcher) {

    public override suspend fun execute(code: String?): Flow<RechargeUiIntent> = flow {
        if (code.isNullOrEmpty() || code.length < 15) {
            emit(
                RechargeUiIntent(
                    statusMsg = "Invalid, code must be 14 number length",
                    isEnable = false,
                    color = Color.Red
                )
            )
        } else {
            emit(
                RechargeUiIntent(
                    statusMsg = "Valid",
                    isEnable = true,
                    color = Color.Green
                )
            )
        }

    }


}

