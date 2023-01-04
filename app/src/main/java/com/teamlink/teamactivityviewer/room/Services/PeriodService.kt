package com.teamlink.teamactivityviewer.room.Services

import android.app.Application
import com.teamlink.teamactivityviewer.room.entity.CustomPeriodEntity
import com.teamlink.teamactivityviewer.services.DataProvider
import kotlinx.coroutines.flow.Flow

class PeriodService(application: Application) {
    var periodDb = DataProvider.getInstance(application).periodDao()

    fun insert(vararg period: CustomPeriodEntity){
        periodDb.insertAll(*period)
    }

    fun all(eventId: String) : List<CustomPeriodEntity> {
        return periodDb.getByEventId(eventId)
    }

    fun deleteAll() {
        periodDb.deleteAll()
    }
}