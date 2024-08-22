package com.example.recharge.feature.recharge.presentation

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.recharge.feature.recharge.presentation.screens.RechargeRoute
import com.example.recharge.feature.recharge.utils.RechargeTestBase
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test


@HiltAndroidTest
class BalanceTabTest: RechargeTestBase() {

    @Test
    fun testAmountInput() {
        with(composeTestRule) {
            // Set up the content to be tested
            setContent {
                RechargeRoute(
                    onBackClick = {},
                    rechargeModel = rechargeModel
                )
            }

            // Navigate to the "Balance" tab if needed
            onNodeWithText("Balance").performClick()

            onNodeWithTag("AmountInputField").assertExists()
            waitForIdle()
            onNodeWithTag("AmountInputField")
                .performTextInput("5")
            // Verify that the input field contains the expected text
            onNodeWithTag("AmountInputField")
                .assertTextEquals("5", "SAR")
        }
    }

    @Test
    fun testAmountSuggestAction() {
        with(composeTestRule) {
            // Set up the content to be tested
            setContent {
                RechargeRoute(
                    onBackClick = {},
                    rechargeModel = rechargeModel
                )
            }

            // Navigate to the "Balance" tab if needed
            onNodeWithText("Balance").performClick()
            onNodeWithTag("ChargeButton")
                .assertExists()
                .assertIsNotEnabled()

            onNodeWithTag("Amount50").assertExists()
            onNodeWithTag("Amount50").performClick()
            waitForIdle()
            onNodeWithTag("ChargeButton").assertIsEnabled()
            // Verify that the input field contains the expected text
            onNodeWithTag("AmountInputField")
                .assertTextEquals("50", "SAR")
        }
    }


}