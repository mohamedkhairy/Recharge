package com.example.home.presentation.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home.domain.useCase.EnterPhoneNumberUseCase
import com.example.home.presentation.utils.HomeConstant.DIALOG_STATE
import com.example.home.presentation.utils.HomeConstant.PHONE_QUERY
import com.example.home.presentation.utils.HomeConstant.VALIDATION_QUERY_MIN_LENGTH
import com.example.core.sharedData.RechargeModel
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
class HomeViewModel @Inject constructor(
    private val phoneNumberUseCase: EnterPhoneNumberUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {


    val phoneNumber = savedStateHandle.getStateFlow(key = PHONE_QUERY, initialValue = "")
    val actionState = savedStateHandle.getStateFlow(key = DIALOG_STATE, initialValue = ActionState.NONE)


    val searchResultUiState: StateFlow<UiState<RechargeModel>> =
        phoneNumber.flatMapLatest { query ->
            if (query.length < VALIDATION_QUERY_MIN_LENGTH) {
                flowOf(UiState.Ideal())
            } else {
                phoneNumberUseCase(query)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Ideal(),
        )


    fun onSearchQueryChanged(query: String?) {
        savedStateHandle[PHONE_QUERY] = query
    }

    fun onActionStateChanged(actionState: ActionState) {
        savedStateHandle[DIALOG_STATE] = actionState
    }

}
