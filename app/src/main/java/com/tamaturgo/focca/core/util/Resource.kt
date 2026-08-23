package com.tamaturgo.focca.core.util

/**
 * Generic wrapper for a use case result, letting a ViewModel represent loading/success/error
 * as a single state without throwing exceptions across layers.
 */
sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val message: String, val throwable: Throwable? = null) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
}
