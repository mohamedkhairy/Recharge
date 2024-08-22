package com.example.home.presentation.screen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.home.domain.useCase.EnterPhoneNumberUseCase
import com.example.home.fakeData.ZainInvalidFakeData.invalidPhoneNum
import com.example.home.fakeData.ZainValidFakeData.validPhoneNum
import com.example.home.presentation.screens.HomeViewModel
import com.example.home.presentation.utils.HomeConstant.DIALOG_STATE
import com.example.home.presentation.utils.HomeConstant.PHONE_QUERY
import com.example.core.sharedData.RechargeModel
import com.example.recharge.feature.home.R
import com.example.utils.core.ActionState
import com.example.utils.core.UiState
import com.example.utils.resourceProvider.ResourceProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val instantExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: HomeViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var resourceProvider: ResourceProvider

    private lateinit var enterPhoneNumberUseCase: EnterPhoneNumberUseCase


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined) // Set the main dispatcher for tests
        enterPhoneNumberUseCase = EnterPhoneNumberUseCase(Dispatchers.Unconfined, resourceProvider)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher after the test
    }

    @Test
    fun `when phone number is shorter than VALIDATION_QUERY_MIN_LENGTH, UiState is Ideal`() = runTest(testDispatcher) {
        savedStateHandle = SavedStateHandle(mapOf(PHONE_QUERY to "12"))
        viewModel = HomeViewModel(enterPhoneNumberUseCase, savedStateHandle)

        viewModel.searchResultUiState.test {
            assertEquals(UiState.Ideal<RechargeModel>(), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when phone number is valid, UiState is updated based on use case result`() = runTest(testDispatcher) {
        val expectedUiState = UiState.success(RechargeModel(validPhoneNum, "Voice"))

        savedStateHandle = SavedStateHandle(mapOf(PHONE_QUERY to validPhoneNum))
        viewModel = HomeViewModel(enterPhoneNumberUseCase, savedStateHandle)


        viewModel.searchResultUiState.test {
            assertEquals(expectedUiState, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

    }

    @Test
    fun `when phone number is invalid, UiState is error result`() = runTest(testDispatcher) {
        val errorMessage = "Invalid Zain number"

        `when`(resourceProvider.getText(R.string.zain_validation_message)).thenReturn(errorMessage)

        savedStateHandle = SavedStateHandle(mapOf(PHONE_QUERY to invalidPhoneNum))

        viewModel = HomeViewModel(enterPhoneNumberUseCase, savedStateHandle)


        viewModel.searchResultUiState.test {
            assertTrue { awaitItem() is UiState.Error }
            cancelAndIgnoreRemainingEvents()
        }

    }

    @Test
    fun `onSearchQueryChanged updates phone number in SavedStateHandle`() = runTest(testDispatcher) {
        savedStateHandle = SavedStateHandle()
        viewModel = HomeViewModel(enterPhoneNumberUseCase, savedStateHandle)

        viewModel.onSearchQueryChanged(validPhoneNum)

        assertEquals(validPhoneNum, savedStateHandle[PHONE_QUERY])
    }

    @Test
    fun `onActionStateChanged updates action state in SavedStateHandle`() = runTest(testDispatcher) {
        savedStateHandle = SavedStateHandle()
        viewModel = HomeViewModel(enterPhoneNumberUseCase, savedStateHandle)

        val newActionState = ActionState.ACTION
        viewModel.onActionStateChanged(newActionState)

        assertEquals(newActionState, savedStateHandle[DIALOG_STATE])
    }
}
