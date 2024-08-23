package com.example.recharge.feature.recharge.presentation.util

import androidx.annotation.ColorRes
import androidx.compose.ui.graphics.Color

/**
 * Data class representing the UI intent for recharge operations.
 *
 * @property statusMsg Optional message indicating the status of the operation.
 * @property isEnable Indicates whether the operation is enabled.
 * @property color Resource ID for the color associated with the UI intent, defaults to DarkGray.
 */
data class RechargeUiIntent(
    val statusMsg: String? = null,
    val isEnable: Boolean = false,
    @ColorRes val color: Color = Color.DarkGray
)
