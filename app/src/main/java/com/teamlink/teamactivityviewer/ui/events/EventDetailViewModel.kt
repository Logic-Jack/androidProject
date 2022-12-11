package com.teamlink.teamactivityviewer.ui.events

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.teamlink.teamactivityviewer.room.Services.EventService

class EventDetailViewModel(application: Application): AndroidViewModel(application) {

    val eventService = EventService(application)



}