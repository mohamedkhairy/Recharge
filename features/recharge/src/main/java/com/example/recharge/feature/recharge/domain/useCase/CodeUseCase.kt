package com.example.recharge.feature.recharge.domain.useCase

import androidx.compose.ui.graphics.Color
import com.example.recharge.feature.recharge.presentation.util.RechargeUiIntent
import com.example.utils.usecases.FlowUseCase
import com.example.utils.dispatchers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

open class CodeUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : FlowUseCase<String?, RechargeUiIntent>(ioDispatcher) {

    public override suspend fun execute(code: String?): Flow<RechargeUiIntent> = flow {
        code?.let{
            if (code.length < 15){
                emit(
                    RechargeUiIntent(
                        statusMsg = "Invalid, code must be 14 number length",
                        isEnable = false,
                        color = Color.Red
                    )
                )
            } else
                emit(
                    RechargeUiIntent(
                        statusMsg = "Valid",
                        isEnable = true,
                        color = Color.Green
                    )
                )
        } ?: run { error("Invalid, code must be 14 number length") }

//        if (code.isNullOrEmpty() || code.length < 15) {
//            emit(
//                RechargeUiIntent(
//                    statusMsg = "Invalid, code must be 14 number length",
//                    isEnable = false,
//                    color = Color.Red
//                )
//            )
//        } else {
//            emit(
//                RechargeUiIntent(
//                    statusMsg = "Valid",
//                    isEnable = true,
//                    color = Color.Green
//                )
//            )
//        }
//
    }


}

