package com.teamlink.teamactivityviewer.ui.data.model

import java.util.*

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val succesfullLogin: Boolean,
    val userId: String?,
    val userName: String?,
    val token: String?,
    val validTill: Date,
    val refreshToken: String?
)