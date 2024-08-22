package com.example.recharge.feature.recharge.utils

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.core.sharedData.RechargeModel
import com.example.recharge.feature.recharge.AndroidTestActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
abstract class RechargeTestBase {

        @get:Rule(order = 0)
        val hiltRule = HiltAndroidRule(this)

        @get:Rule(order = 1)
        val composeTestRule = createAndroidComposeRule<AndroidTestActivity>()

        lateinit var rechargeModel: RechargeModel


        @Before
        fun init() {
            hiltRule.inject()
            rechargeModel = RechargeModel("0586677881", "Voice")
        }
}