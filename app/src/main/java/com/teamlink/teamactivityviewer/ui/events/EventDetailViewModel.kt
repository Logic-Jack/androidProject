package com.teamlink.teamactivityviewer.ui.events

import android.app.Application
import androidx.lifecycle.*
import com.teamlink.teamactivityviewer.room.Services.EventService
import com.teamlink.teamactivityviewer.room.Services.PeriodService
import com.teamlink.teamactivityviewer.room.entity.CustomPeriodEntity
import com.teamlink.teamactivityviewer.room.entity.EventEntity
import com.teamlink.teamactivityviewer.ui.data.model.CustomPeriod
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventDetailViewModel(application: Application): AndroidViewModel(application) {

    private val eventService = EventService(application)
    private val periodService = PeriodService(application)

    val event: MutableLiveData<EventEntity?> = MutableLiveData<EventEntity?>()
    val periods: MutableLiveData<List<CustomPeriodEntity>> = MutableLiveData<List<CustomPeriodEntity>>()

    fun oncreate(eventId: String){
        periods.value = emptyList()
        viewModelScope.launch(Dispatchers.IO) {
            val eventEntity = eventService.getById(eventId)
            val periodEntities = periodService.all(eventId)

            viewModelScope.launch(Dispatchers.Main){
                event.value = eventEntity
                periods.value = periodEntities
            }
        }

    }
}