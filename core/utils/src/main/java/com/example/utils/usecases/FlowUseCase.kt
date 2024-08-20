package com.example.utils.usecases

import com.example.utils.core.UiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

abstract class FlowUseCase<in Params, Result>(private val coroutineDispatcher: CoroutineDispatcher) {
    @ExperimentalCoroutinesApi
    suspend operator fun invoke(parameters: Params): Flow<UiState<Result>> =
        execute(parameters)
            .obtainOutcome()
            .flowOn(coroutineDispatcher)

    protected abstract suspend fun execute(parameters: Params): Flow<Result>
}

fun <T> Flow<T>.obtainOutcome(): Flow<UiState<T>> =
    this.map { UiState.success(it) }
        .onStart { emit(UiState.Loading(true)) }
        .catch { e ->
            emit(UiState.Error(e)) }
