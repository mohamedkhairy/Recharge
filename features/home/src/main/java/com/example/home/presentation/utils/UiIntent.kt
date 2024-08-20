package com.example.home.presentation.utils

import androidx.annotation.ColorRes
import androidx.compose.ui.graphics.Color
import com.example.sharedData.model.RechargeModel

data class UiIntent(
    val text: String? = null,
    @ColorRes val color: Color = Color.LightGray,
    val rechargeModel: RechargeModel? = null
)
