package com.teamlink.teamactivityviewer.room.Services

import android.app.Application
import com.teamlink.teamactivityviewer.room.EventDao
import com.teamlink.teamactivityviewer.room.entity.EventEntity
import com.teamlink.teamactivityviewer.services.DataProvider
import kotlinx.coroutines.flow.Flow

class EventService(application: Application) {
    var eventDb = DataProvider.getInstance(application).eventDao()

    fun all(): Flow<List<EventEntity>> {
        return eventDb.getAll()
    }

    fun insert(vararg event: EventEntity){
        eventDb.insertAll(*event)
    }

    fun insert(event: EventEntity){
        eventDb.insert(event)
    }

    fun getById(eventId: String) : EventEntity? {
        return eventDb.getById(eventId)
    }

    fun delete(event: EventEntity) {
        eventDb.delete(event)
    }

    fun deleteAll(){
        eventDb.deleteAll()
    }
}