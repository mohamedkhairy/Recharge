package com.example.recharge.feature.recharge.domain.useCase

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith

/**
 * Unit tests for the AmountUseCase class, validating its behavior based on different input amounts.
 *
 * Tests include:
 * - Handling of null amounts
 * - Emission of RechargeUiIntent with appropriate `isEnable` values based on amount thresholds
 */
@OptIn(ExperimentalCoroutinesApi::class)
class AmountUseCaseTest {

    private lateinit var amountUseCase: AmountUseCase

    private val testDispatcher = UnconfinedTestDispatcher()


    @Before
    fun setUp() {
        amountUseCase = AmountUseCase(testDispatcher)
    }

    @Test
    fun `when amount is null, throws error`() = runTest(testDispatcher) {
        val errorMessage = "Invalid Amount"
        val exception = assertFailsWith<IllegalStateException> {
            amountUseCase.execute(null).collect()
        }
        assertEquals(errorMessage, exception.message)
    }

    @Test
    fun `when amount is less than 25 emits RechargeUiIntent with isEnable false`() = runTest(testDispatcher) {
        val result = amountUseCase.execute("20").first()
        assertEquals(false, result.isEnable)
    }

    @Test
    fun `when amount is equal to 25, emits RechargeUiIntent with isEnable true`() = runTest(testDispatcher) {
        val result = amountUseCase.execute("25").first()
        assertEquals(true, result.isEnable)
    }

    @Test
    fun `when amount is greater than 25, emits RechargeUiIntent with isEnable true`() = runTest(testDispatcher) {
        val result = amountUseCase.execute("30").first()
        assertEquals(true, result.isEnable)
    }

    @Test
    fun `when amount is less than 25, emits RechargeUiIntent with isEnable false`() = runTest(testDispatcher) {
        val result = amountUseCase.execute("10").first()
        assertEquals(false, result.isEnable)
    }
}
