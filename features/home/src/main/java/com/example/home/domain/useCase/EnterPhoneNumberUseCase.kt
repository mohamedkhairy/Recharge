package com.example.home.domain.useCase

import com.example.core.sharedData.RechargeModel
import com.example.recharge.feature.home.R
import com.example.utils.core.Constant.zainRecognizekey
import com.example.utils.core.isZainNumber
import com.example.utils.dispatchers.IoDispatcher
import com.example.utils.resourceProvider.ResourceProvider
import com.example.utils.usecases.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EnterPhoneNumberUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val resourceProvider: ResourceProvider
) : FlowUseCase<String?, RechargeModel>(ioDispatcher) {

     public override suspend fun execute(phoneNum: String?): Flow<RechargeModel> = flow {
        if (phoneNum.isNullOrEmpty() || phoneNum.length < 10){
            error(resourceProvider.getText(R.string.phone_num_length_message))
        }
        else if (!phoneNum.isZainNumber()){
            error("${resourceProvider.getText(R.string.zain_validation_message)} ${zainRecognizekey}")
        }
        else
        emit(
            RechargeModel(
                phoneNum = phoneNum,
                rechargeType = "Voice"
            )
        )
    }


}

