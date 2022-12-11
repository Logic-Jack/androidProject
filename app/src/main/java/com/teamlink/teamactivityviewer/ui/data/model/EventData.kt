package com.teamlink.teamactivityviewer.ui.data.model

import java.util.*

data class EventData(
    val Id: String,
    val Name: String,
    val Description: String,
    val streetName: String,
    val HouseNumber: String,
    val City: String,
    val PostalCode: String,
    val Country: String
)


data class CustomPeriod(
    val Id: String,
    val StartDate: Date,
    val EndDate: Date
)

