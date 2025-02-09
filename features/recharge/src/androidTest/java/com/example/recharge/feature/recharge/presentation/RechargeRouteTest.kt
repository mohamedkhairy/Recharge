package com.example.recharge.feature.recharge.presentation

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.recharge.feature.recharge.presentation.screens.RechargeRoute
import com.example.recharge.feature.recharge.utils.RechargeTestBase
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test


/**
 * ZainRechargeRouteTest contains UI tests for the Recharge screen's tab navigation.
 * It uses Hilt for dependency injection and Compose Test Rules for UI testing.
 */
@HiltAndroidTest
class ZainRechargeRouteTest: RechargeTestBase() {


    /**
     * - `testInitialScreen()`: Checks initial screen state.
     *   1. Verifies that the "Balance" tab is selected by default.
     *   2. Confirms the "Voucher" tab is not selected by default.
     *   3. Ensures the back button is present and clickable.
     */
    @Test
    fun testInitialScreen() {
        // Set up the content to be tested
        with(composeTestRule) {
            setContent {
                RechargeRoute(
                    onBackClick = {},
                    rechargeModel = rechargeModel
                )
            }

            // Verify that the "Balance" tab is selected by default
            onNodeWithText("Balance")
                .assertIsSelected()

            // Verify that the "Voucher" tab is not selected by default
            onNodeWithText("Voucher")
                .assertIsNotSelected()

            // Verify that the back button is present and clickable
            onNodeWithContentDescription("Back")
                .assertHasClickAction()
        }
    }


    /**
     * - `testTabSelection()`: Tests tab selection functionality.
     *   1. Clicks on the "Voucher" tab.
     *   2. Verifies the "Voucher" tab is selected and the "Balance" tab is not.
     */
    @Test
    fun testTabSelection() {
        // Set up the content to be tested
        with(composeTestRule) {
            setContent {
                RechargeRoute(
                    onBackClick = {},
                    rechargeModel = rechargeModel
                )
            }

            // Click on the "Voucher" tab
            onNodeWithText("Voucher")
                .performClick()

            // Verify that the "Voucher" tab is now selected
            onNodeWithText("Voucher")
                .assertIsSelected()

            // Verify that the "Balance" tab is not selected
            onNodeWithText("Balance")
                .assertIsNotSelected()
        }
    }

}
