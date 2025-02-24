package com.vk.domain.utils

sealed class CustomResult<T>(val data: T? = null, val issueType: NetworkIssues? = null) {
    class Success<T>(data: T?): CustomResult<T>(data)
    class Error<T>(issueType: NetworkIssues?): CustomResult<T>(null, issueType)
}