package com.example.recharge.feature.recharge.presentation

import androidx.compose.ui.test.*
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.core.sharedData.RechargeModel
import com.example.recharge.feature.recharge.AndroidTestActivity
import com.example.recharge.feature.recharge.presentation.screens.RechargeRoute
import com.example.recharge.feature.recharge.utils.RechargeTestBase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
class ZainRechargeRouteTest: RechargeTestBase() {

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
