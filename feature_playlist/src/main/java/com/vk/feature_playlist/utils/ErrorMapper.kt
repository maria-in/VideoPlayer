package com.vk.feature_playlist.utils

import com.vk.domain.utils.NetworkIssues
import com.vk.resources.R

fun NetworkIssues.toStringRes(): Int {
    return when(this) {
        NetworkIssues.NO_INTERNET_ERROR -> R.string.no_internet_error
        else -> R.string.other_error
    }
}