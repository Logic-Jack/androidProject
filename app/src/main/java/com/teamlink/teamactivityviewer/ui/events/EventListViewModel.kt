package com.teamlink.teamactivityviewer.ui.events

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.teamlink.teamactivityviewer.room.Services.EventService
import com.teamlink.teamactivityviewer.room.entity.EventEntity
import com.teamlink.teamactivityviewer.services.ApiService
import com.teamlink.teamactivityviewer.services.DataProvider
import com.teamlink.teamactivityviewer.ui.data.LoginDataSource
import com.teamlink.teamactivityviewer.ui.data.LoginRepository
import com.teamlink.teamactivityviewer.ui.data.model.ClubData
import com.teamlink.teamactivityviewer.ui.data.model.EventData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.reflect.Type

class EventListViewModel(application: Application): AndroidViewModel(application){
    var application2 = application
    var eventService = EventService(application)

    fun onCreate(clubId: String){
        getEvents(clubId)
    }

    private fun getEvents(clubId: String): List<ClubData>{
        viewModelScope.launch(Dispatchers.IO) {
            val repo = LoginRepository(LoginDataSource(), application2)
            // repo.getUser()
            val response = ApiService().getEventsFromClub(clubId, repo.user?.token!!)
            if (response != null){
                eventService.deleteAll()
                val listType: Type = object: TypeToken<List<EventData>>() {}.type
                val events = Gson().fromJson<List<EventData>>(response, listType)

                for (event in events){
                    var entity = EventEntity(event.Id, event.Name, event.Description, event.streetName, event.HouseNumber, event.City, event.PostalCode, event.Country)

                    eventService.insert(entity)
                }
            }
        }

        return emptyList()
    }
}