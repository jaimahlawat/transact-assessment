package com.transact.assessment.common

sealed class Result<out T> {

    data class Success<out T>(val data: T) : Result<T>()

    data class Error(
        val exception: Exception,
        val errorMessage: String,
    ) : Result<Nothing>()

    data object Loading: Result<Nothing>()
}
