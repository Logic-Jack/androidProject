package com.teamlink.teamactivityviewer.ui.events

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.teamlink.teamactivityviewer.room.Services.EventService
import com.teamlink.teamactivityviewer.room.Services.PeriodService
import com.teamlink.teamactivityviewer.room.entity.CustomPeriodEntity
import com.teamlink.teamactivityviewer.room.entity.EventEntity
import com.teamlink.teamactivityviewer.services.ApiService
import com.teamlink.teamactivityviewer.ui.data.LoginDataSource
import com.teamlink.teamactivityviewer.ui.data.LoginRepository
import com.teamlink.teamactivityviewer.ui.data.model.ClubData
import com.teamlink.teamactivityviewer.ui.data.model.EventData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

class EventListViewModel(application: Application): AndroidViewModel(application){
    var application2 = application
    var eventService = EventService(application)
    val periodService = PeriodService(application)
    val events: LiveData<List<EventEntity>> = eventService.all().asLiveData()

    fun onCreate(clubId: String){
        getEvents(clubId)
    }

    private fun getEvents(clubId: String): List<ClubData>{
        viewModelScope.launch(Dispatchers.IO) {
            val repo = LoginRepository(LoginDataSource(), application2)
            val user = repo.getUser()
            try {
                val response = ApiService().getEventsFromClub(clubId, user?.token!!)
                if (response != null){
                    eventService.deleteAll()
                    periodService.deleteAll()
                    val listType: Type = object: TypeToken<List<EventData>>() {}.type
                    val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
                    val events = gson.fromJson<List<EventData>>(response, listType)

                    for (event in events){
                        val entity = EventEntity(event.id, event.name, event.description, event.streetName, event.houseNumber, event.city, event.postalCode, event.country)
                        val format = SimpleDateFormat("dd MMM,yyyy hh:mm a", Locale.getDefault())
                        val periods = event.periods.map {
                            CustomPeriodEntity(it.id, event.id, format.format(it.startDate), format.format(it.endDate))
                        }
                        eventService.insert(entity)
                        periodService.insert(*periods.toTypedArray())
                    }
                }
            }catch (ex: Exception){
                println(ex.message)
                showMessage("failed retrieving events")
            }

        }

        return emptyList()
    }

    private fun showMessage(messageString: String) {
        viewModelScope.launch(Dispatchers.Main){
            Toast.makeText(application2.applicationContext, messageString, Toast.LENGTH_SHORT).show()
        }
    }
}