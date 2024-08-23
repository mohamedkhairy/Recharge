package com.example.recharge.feature.recharge.presentation

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsFocused
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
class VoucherTabTest : RechargeTestBase() {


    /**
     * VoucherTabTest contains UI tests for the Voucher tab in the Recharge screen.
     * It uses Hilt for dependency injection and Compose Test Rules for UI testing.
     *
     * - `testCodeInput()`: Verifies voucher code input functionality.
     *   1. Sets up the RechargeRoute with test content.
     *   2. Clicks on the "Voucher" tab to display the code input.
     *   3. Ensures the code input field and submit button are present and the status text is initially absent.
     *   4. Inputs a voucher code and verifies the field shows the entered code.
     *   5. Checks that the submit button is enabled and status text displays "Valid".
     */

    @Test
    fun testCodeInput() {
        // Set up the content to be tested
        with(composeTestRule) {
            setContent {
                RechargeRoute(
                    onBackClick = {},
                    rechargeModel = rechargeModel
                )
            }

            // Click on the "Voucher" tab to show the code input
            onNodeWithText("Voucher").performClick()

            // Find the code input field (assuming it is implemented as a TextField or similar)
            // This may need to be updated depending on your actual implementation
            onNodeWithTag("CodeInputField").assertExists()
            onNodeWithTag("statusText").assertDoesNotExist()
            onNodeWithTag("SubmitButton").assertExists().assertIsNotEnabled()
            onNodeWithTag("CodeInputField").performTextInput("012345678911111")

            waitForIdle()

            // Verify that the input field contains the expected text
            onNodeWithTag("CodeInputField")
                .assertIsFocused()
                .assertTextEquals("Enter Voucher Code", "012345678911111")


            onNodeWithTag("SubmitButton").assertIsEnabled()
            onNodeWithTag("statusText").assertExists().assertTextEquals("Valid")

        }
    }
}