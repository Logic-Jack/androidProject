package com.teamlink.teamactivityviewer.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clubs")
data class ClubEntity(
    @PrimaryKey val Id: String,
    @ColumnInfo(name = "name") val Name: String,
    @ColumnInfo(name = "description") val Description: String?,
    @ColumnInfo(name = "streetName") val StreetName: String,
    @ColumnInfo(name = "houseNumber") val HouseNumber: String,
    @ColumnInfo(name = "city") val City: String,
    @ColumnInfo(name = "postalCode") val PostalCode: String,
    @ColumnInfo(name = "country") val Country: String
)
