package com.teamlink.teamactivityviewer.services

import androidx.lifecycle.MutableLiveData
import com.teamlink.teamactivityviewer.ui.data.model.ClubData
import com.teamlink.teamactivityviewer.ui.data.model.EventData

class DataProvider private constructor() {

    var clubs: MutableLiveData<List<ClubData>> = MutableLiveData<List<ClubData>>()
    var events: MutableLiveData<List<EventData>> = MutableLiveData<List<EventData>>()

    fun getEvent(eventId: String) : EventData? {
        return events.value?.find { eventData -> eventData.Id == eventId }
    }

    fun getClub(clubId: String) : ClubData? {
        return clubs.value?.find { clubData -> clubData.Id == clubId }
    }

    companion object {
        @Volatile
        private lateinit var instance: DataProvider

        fun getInstance(): DataProvider {
            synchronized(this){
                instance = DataProvider()
            }
            return instance
        }
    }
}