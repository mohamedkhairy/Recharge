package com.example.recharge.feature.recharge.di

import androidx.lifecycle.SavedStateHandle
import com.example.recharge.feature.recharge.domain.useCase.AmountUseCase
import com.example.recharge.feature.recharge.domain.useCase.CodeUseCase
import com.example.recharge.feature.recharge.presentation.di.UseCaseModule
import com.example.recharge.feature.recharge.presentation.util.RechargeUiIntent
import com.example.utils.dispatchers.DefaultDispatcher
import com.example.utils.dispatchers.DispatcherModule
import com.example.utils.dispatchers.IoDispatcher
import com.example.utils.dispatchers.MainDispatcher
import com.example.utils.usecases.FlowUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import org.mockito.Mockito
import org.mockito.Mockito.mock
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [UseCaseModule::class]
)
object TestModule {


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

//
//@Module
//@TestInstallIn(
//    components = [SingletonComponent::class],
//    replaces = [DispatcherModule::class]
//)
//object DispatcherTestModule {
//
//    @Provides
//    @DefaultDispatcher
//    fun providesDefaultDispatcher(): CoroutineDispatcher = mock(Dispatchers.Default)
//
//    @Provides
//    @IoDispatcher
//    fun providesIoDispatcher(): CoroutineDispatcher = mock(Dispatchers.IO)
//
//    @Provides
//    @MainDispatcher
//    fun providesMainDispatcher(): CoroutineDispatcher = mock(Dispatchers.Main)
//}