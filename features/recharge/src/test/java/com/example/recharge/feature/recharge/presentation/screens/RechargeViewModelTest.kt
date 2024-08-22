package com.example.recharge.feature.recharge.presentation.screens
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.recharge.feature.recharge.domain.useCase.AmountUseCase
import com.example.recharge.feature.recharge.domain.useCase.CodeUseCase
import com.example.recharge.feature.recharge.fakeData.InvalidFakeData.invalidAmount
import com.example.recharge.feature.recharge.fakeData.InvalidFakeData.invalidCode
import com.example.recharge.feature.recharge.fakeData.ValidFakeData.validAmount
import com.example.recharge.feature.recharge.fakeData.ValidFakeData.validCode
import com.example.recharge.feature.recharge.presentation.util.RechargeUiIntent
import com.example.utils.core.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class RechargeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    private val amountUseCase: AmountUseCase = Mockito.mock(AmountUseCase::class.java)
    private val codeUseCase: CodeUseCase = Mockito.mock(CodeUseCase::class.java)
    private val savedStateHandle: SavedStateHandle = SavedStateHandle()
    private val idealState = UiState.Ideal<RechargeUiIntent>()
    private val successState = UiState.Success(RechargeUiIntent(isEnable = true))

    private lateinit var viewModel: RechargeViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(UnconfinedTestDispatcher()) // Set up the test dispatcher
        viewModel = RechargeViewModel(amountUseCase, codeUseCase, savedStateHandle)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the dispatcher
    }


    @Test
    fun `amountSavedState should have initial value`() = runTest {
        viewModel.amountUiState.test {
            // Check initial value
            assertEquals(idealState, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `codeSavedState should have initial value`() = runTest {
        viewModel.codeUiState.test {
            // Check initial value
            assertEquals(idealState, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `amountSavedState should update correctly`() = runTest {

        `when`(amountUseCase(validAmount)).thenReturn(flowOf(successState))

        viewModel.amountUiState.test {
            // Check initial value
            assertEquals(idealState, awaitItem())

            // Update state
            viewModel.onAmountChanged(validAmount)

            // Collect new value
            assertEquals(successState, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `codeSavedState should update correctly`() = runTest {

        `when`(codeUseCase(validCode)).thenReturn(flowOf(successState))

        viewModel.codeUiState.test {
            // Check initial value
            assertEquals(idealState, awaitItem())

            // Update state
            viewModel.onCodeChanged(validCode)

            // Collect new value
            assertEquals(successState, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `amountSavedState should handle amount less than minimum length`() = runTest(testDispatcher) {

        `when`(amountUseCase(invalidAmount)).thenReturn(flowOf(idealState))

        viewModel.amountUiState.test {
            // Update state
            viewModel.onAmountChanged(invalidAmount)

            // Collect new value
            assertEquals(idealState, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `codeSavedState should handle code less than minimum length`() = runTest {

        `when`(codeUseCase(invalidCode)).thenReturn(flowOf(idealState))

        viewModel.codeUiState.test {

            // Update state
            viewModel.onCodeChanged(invalidCode)

            // Collect new value
            assertEquals(idealState, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }


}
