package com.example.recharge.presentation.util

import androidx.annotation.ColorRes
import androidx.compose.ui.graphics.Color
import com.example.sharedData.model.RechargeModel

data class RechargeUiIntent(
    val statusMsg: String? = null,
    val isEnable: Boolean = false,
    @ColorRes val color: Color = Color.DarkGray
)
