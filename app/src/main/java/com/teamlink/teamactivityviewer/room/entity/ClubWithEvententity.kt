package com.teamlink.teamactivityviewer.room.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.teamlink.teamactivityviewer.ui.data.model.CustomPeriod
import com.teamlink.teamactivityviewer.ui.data.model.EventData

@Entity
data class ClubWithEvententity(
    @Embedded val eventEntity: EventEntity,
    @Relation(
        parentColumn = "Id",
        entityColumn = "EventId"
    )
    val customPeriods : List<CustomPeriod>
)
