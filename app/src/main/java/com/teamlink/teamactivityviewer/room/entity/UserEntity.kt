package com.teamlink.teamactivityviewer.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val userId: String,
    val succesfullLogin: Boolean,
    val userName: String?,
    val token: String?,
    val validTill: String?,
    val refreshToken: String?
)