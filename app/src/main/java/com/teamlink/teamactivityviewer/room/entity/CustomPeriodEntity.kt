package com.teamlink.teamactivityviewer.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "periods")
data class CustomPeriodEntity(
    @PrimaryKey val Id: String,
    val EventId: String,
    @ColumnInfo(name = "start_date") val StartDate: String,
    @ColumnInfo(name = "end_date") val EndDate: String
)
