package com.example.utils.core

import androidx.compose.ui.text.input.TextFieldValue

sealed class UiState<T> {


    data class Error<T>(
        val throwable: Throwable? = null,
        val data: T? = null
    ) : UiState<T>()

    data class Success<T>(
        val data: T? = null
    ) : UiState<T>()

    data class Loading<T>(
        var isLoading: Boolean = false
    ) : UiState<T>()

    data class Ideal<T>(
        val value: String? = null
    ) : UiState<T>()


    companion object {


        fun <T> success(data: T): UiState<T> = Success(data)
    }
}

