package com.example.recharge.feature.recharge.domain.useCase

import androidx.compose.ui.graphics.Color
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
 * Unit tests for the CodeUseCase class, validating its behavior based on different input codes.
 *
 * Tests include:
 * - Handling of null codes
 * - Handling of empty codes
 * - Emission of RechargeUiIntent with appropriate status messages, enable states, and colors
 *   based on the length of the code
 */
@OptIn(ExperimentalCoroutinesApi::class)
class CodeUseCaseTest {

    private lateinit var codeUseCase: CodeUseCase
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        codeUseCase = CodeUseCase(testDispatcher)
    }

    @Test
    fun `when code is null, emits invalid RechargeUiIntent`() = runTest(testDispatcher) {
        val errorMessage = "Invalid, code must be 14 number length"
        val exception = assertFailsWith<IllegalStateException> {
            codeUseCase.execute(null).collect()
        }
        assertEquals(errorMessage, exception.message)
    }

    @Test
    fun `when code is empty, emits invalid RechargeUiIntent`() = runTest(testDispatcher) {
        val result = codeUseCase.execute("").first()
        assertEquals("Invalid, code must be 14 number length", result.statusMsg)
        assertEquals(false, result.isEnable)
        assertEquals(Color.Red, result.color)
    }

    @Test
    fun `when code length is less than 15, emits invalid RechargeUiIntent`() = runTest(testDispatcher) {
        val result = codeUseCase.execute("12345678901234").first() // 14 digits
        assertEquals("Invalid, code must be 14 number length", result.statusMsg)
        assertEquals(false, result.isEnable)
        assertEquals(Color.Red, result.color)
    }

    @Test
    fun `when code length is exactly 15, emits valid RechargeUiIntent`() = runTest(testDispatcher) {
        val result = codeUseCase.execute("123456789012345").first() // 15 digits
        assertEquals("Valid", result.statusMsg)
        assertEquals(true, result.isEnable)
        assertEquals(Color.Green, result.color)
    }

    @Test
    fun `when code length is greater than 15, emits valid RechargeUiIntent`() = runTest(testDispatcher) {
        val result = codeUseCase.execute("1234567890123456").first() // More than 15 digits
        assertEquals("Valid", result.statusMsg)
        assertEquals(true, result.isEnable)
        assertEquals(Color.Green, result.color)
    }
}
