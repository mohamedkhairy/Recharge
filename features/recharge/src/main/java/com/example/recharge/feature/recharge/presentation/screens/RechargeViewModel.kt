package com.example.recharge.feature.recharge.presentation.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recharge.feature.recharge.domain.useCase.AmountUseCase
import com.example.recharge.feature.recharge.domain.useCase.CodeUseCase
import com.example.recharge.feature.recharge.presentation.util.RechargeUiIntent
import com.example.utils.core.ActionState
import com.example.utils.core.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class RechargeViewModel @Inject constructor(
    private val amountUseCase: AmountUseCase,
    private val codeUseCase: CodeUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {


    val amountSavedState = savedStateHandle.getStateFlow(key = AMOUNT_KEY, initialValue = "")
    val codeSavedState = savedStateHandle.getStateFlow(key = CODE_KEY, initialValue = "")


    val amountUiState: StateFlow<UiState<RechargeUiIntent>> =
        amountSavedState.flatMapLatest { query ->
            if (query.length < MINIMUM_AMOUNT_LENGTH) {
                flowOf(UiState.Ideal())
            } else {
                amountUseCase(query)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Ideal(),
        )

    val codeUiState: StateFlow<UiState<RechargeUiIntent>> =
        codeSavedState.flatMapLatest { query ->
            if (query.length < MINIMUM_CODE_LENGTH) {
                flowOf(UiState.Ideal())
            } else {
                codeUseCase(query)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Ideal(),
        )


    fun onAmountChanged(amount: String?) {
        savedStateHandle[AMOUNT_KEY] = amount
    }

    fun onCodeChanged(code: String?) {
        savedStateHandle[CODE_KEY] = code
    }

    companion object{
        const val MINIMUM_AMOUNT_LENGTH = 2
        const val MINIMUM_CODE_LENGTH = 10
        const val AMOUNT_KEY = "AMOUNT_KEY"
        const val CODE_KEY = "CODE_KEY"
    }
}
