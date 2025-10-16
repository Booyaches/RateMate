package com.recruitment.network.common

sealed interface NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>
    data class Error(val error: NetworkError) : NetworkResult<Nothing>
    data object Loading : NetworkResult<Nothing>
}

sealed interface NetworkError {
    data class Http(val code: Int, val message: String? = null, val body: String? = null) : NetworkError
    data class NetworkUnavailable(val cause: Throwable? = null) : NetworkError
    data class Timeout(val cause: Throwable? = null) : NetworkError
    data class Serialization(val cause: Throwable? = null) : NetworkError
    data class Unknown(val cause: Throwable? = null) : NetworkError
}