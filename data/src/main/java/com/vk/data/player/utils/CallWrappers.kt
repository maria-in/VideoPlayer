package com.vk.data.player.utils

import com.vk.domain.utils.NetworkIssues
import kotlinx.serialization.SerializationException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun handleResultThrowable(throwable: Throwable): NetworkIssues {
    return when(throwable) {
        is Exception -> handleNetworkError(throwable)
        else -> NetworkIssues.UNKNOWN_ERROR
    }
}

private fun handleNetworkError(exception: Exception): NetworkIssues {
    return when (exception) {
        is SocketTimeoutException -> NetworkIssues.REQUEST_TIMEOUT_ERROR
        is UnknownHostException -> NetworkIssues.NO_INTERNET_ERROR
        is retrofit2.HttpException -> {
            when (exception.code()) {
                in 500..599 -> NetworkIssues.SERVER_ERROR
                else -> NetworkIssues.UNKNOWN_ERROR
            }
        }
        is SerializationException -> NetworkIssues.SERIALIZATION_ERROR
        else -> NetworkIssues.UNKNOWN_ERROR
    }
}