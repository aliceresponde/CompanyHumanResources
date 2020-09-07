package com.example.companyhumanresources.data

sealed class OperationResult<out T> {
    data class Success<out T>(val data: T) : OperationResult<T>()
    data class Error(val exception: Throwable?) : OperationResult<Nothing>()
}