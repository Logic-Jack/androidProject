package com.teamlink.teamactivityviewer.ui.data.model

import java.util.*

data class EventData(
    val id: String,
    val name: String,
    val description: String,
    val streetName: String,
    val houseNumber: String,
    val city: String,
    val postalCode: String,
    val country: String,
    val periods: List<CustomPeriod>
)


data class CustomPeriod(
    val id: String,
    val startDate: Date,
    val endDate: Date
)

