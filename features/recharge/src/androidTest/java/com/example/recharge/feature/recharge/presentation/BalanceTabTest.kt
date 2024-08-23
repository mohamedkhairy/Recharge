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


/**
 * BalanceTabTest contains UI tests for the Balance tab in the Recharge screen.
 * It uses Hilt for dependency injection and Compose Test Rules for UI testing.
 */

@HiltAndroidTest
class BalanceTabTest: RechargeTestBase() {


    /**
     * - `testAmountInput()`: Verifies that the Amount input field accepts and displays text correctly.
     *   1. Sets up the RechargeRoute with test content.
     *   2. Navigates to the "Balance" tab.
     *   3. Inputs "5" into the Amount field and checks the displayed text.
     */

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


    /**
     * - `testAmountSuggestAction()`: Tests the behavior of the amount suggestion action.
     *   1. Sets up the RechargeRoute with test content.
     *   2. Navigates to the "Balance" tab.
     *   3. Checks that the "ChargeButton" is initially disabled.
     *   4. Selects the "Amount50" suggestion and verifies the button becomes enabled.
     *   5. Ensures the Amount field displays "50".
     */
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