package com.thoughtworks.moments.ui.screen

data class MainScreenUiState(
    val isLoadingUser: Boolean = false,
    val isLoadingTweets: Boolean = false,
    val isRefreshing: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: String? = null
)