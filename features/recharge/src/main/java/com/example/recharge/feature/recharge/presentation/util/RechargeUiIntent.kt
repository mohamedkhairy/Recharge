package com.example.recharge.feature.recharge.presentation.util

import androidx.annotation.ColorRes
import androidx.compose.ui.graphics.Color

data class RechargeUiIntent(
    val statusMsg: String? = null,
    val isEnable: Boolean = false,
    @ColorRes val color: Color = Color.DarkGray
)
