package com.teamlink.teamactivityviewer.ui.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

data class UserData(
    val userId: String?,
    val succesfullLogin: Boolean,
    val userName: String?,
    val token: String?,
    val validTill: Date,
    val refreshToken: String?
)
