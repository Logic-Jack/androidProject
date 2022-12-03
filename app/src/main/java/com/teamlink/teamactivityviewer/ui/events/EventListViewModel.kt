package com.teamlink.teamactivityviewer.ui.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.teamlink.teamactivityviewer.services.ApiService
import com.teamlink.teamactivityviewer.services.DataProvider
import com.teamlink.teamactivityviewer.ui.data.model.ClubData
import com.teamlink.teamactivityviewer.ui.data.model.EventData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.reflect.Type

class EventListViewModel: ViewModel(){


    fun onCreate(clubId: String){
        getEvents(clubId)
    }

    private fun getEvents(clubId: String): List<ClubData>{
        viewModelScope.launch(Dispatchers.IO) {
            val response = ApiService().getEventsFromClub(clubId)
            if (response != null){
                viewModelScope.launch(Dispatchers.Main) {
                    val listType: Type = object: TypeToken<List<EventData>>() {}.type
                    DataProvider.getInstance().events.value = Gson().fromJson<List<EventData>>(response, listType)
                }
            }
        }

        return emptyList()
    }
}