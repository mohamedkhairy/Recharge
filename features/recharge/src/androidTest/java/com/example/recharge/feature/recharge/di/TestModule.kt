package com.example.recharge.feature.recharge.di

import com.example.recharge.feature.recharge.domain.useCase.AmountUseCase
import com.example.recharge.feature.recharge.domain.useCase.CodeUseCase
import com.example.recharge.feature.recharge.presentation.di.UseCaseModule
import com.example.recharge.feature.recharge.presentation.util.RechargeUiIntent
import com.example.utils.usecases.FlowUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import org.mockito.Mockito
import org.mockito.Mockito.mock

/**
 * TestModule provides mock implementations of use cases for testing, replacing the original `UseCaseModule`.
 * Installed in the SingletonComponent, this module is used exclusively in tests.
 */

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [UseCaseModule::class]
)
object TestModule {


    /**
        * - `AmountUseCase`: Mocks `FlowUseCase<String?, RechargeUiIntent>`.
     *   - When `execute("50")` is called, it returns a flow emitting `RechargeUiIntent("Mocked Data", true)`.
     */
    @Provides
    fun provideAmountUseCase(): FlowUseCase<String?, RechargeUiIntent> {
        // Provide a mock or a test implementation
        return mock(AmountUseCase::class.java).apply {
            runBlocking {
                Mockito.`when`(execute("50")).thenReturn(
                    MutableStateFlow(
                        RechargeUiIntent("Mocked Data", true)
                    )
                )
            }
        }
    }


    /**
     * - `CodeUseCase`: Mocks `FlowUseCase<String?, RechargeUiIntent>`.
     *   - When `execute("012345678911111")` is called,
     *   it returns a flow emitting `RechargeUiIntent("Valid", true)`.
     */
    @Provides
    fun provideCodeUseCase(): FlowUseCase<String?, RechargeUiIntent> {
        // Provide a mock or a test implementation
        return mock(CodeUseCase::class.java).apply {
            runBlocking {
                Mockito.`when`(execute("012345678911111")).thenReturn(
                    MutableStateFlow(
                        RechargeUiIntent("Valid", true)
                    )
                )
            }
        }
    }
}
