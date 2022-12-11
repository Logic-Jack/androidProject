package com.teamlink.teamactivityviewer.room.entity

import androidx.lifecycle.LiveData
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey val Id: String,
    @ColumnInfo(name = "name") val Name: String,
    @ColumnInfo(name = "description") val Description: String,
    //@Ignore val Periods: LiveData<List<CustomPeriodEntity>>?,
    @ColumnInfo(name = "street_name") val StreetName: String,
    @ColumnInfo(name = "house_number") val HouseNumber: String,
    @ColumnInfo(name = "city") val City: String,
    @ColumnInfo(name = "postal_code") val PostalCode: String,
    @ColumnInfo(name = "country") val Country: String
)
