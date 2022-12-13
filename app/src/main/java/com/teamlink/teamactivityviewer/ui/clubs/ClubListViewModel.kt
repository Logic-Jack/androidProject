package com.teamlink.teamactivityviewer.ui.clubs

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.teamlink.teamactivityviewer.room.Services.ClubDaoService
import com.teamlink.teamactivityviewer.room.entity.ClubEntity
import com.teamlink.teamactivityviewer.services.ApiService
import com.teamlink.teamactivityviewer.ui.data.LoginDataSource
import com.teamlink.teamactivityviewer.ui.data.LoginRepository
import com.teamlink.teamactivityviewer.ui.data.model.ClubData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.reflect.Type

class ClubListViewModel(application: Application) : AndroidViewModel(application) {

    private val application2 = application

    var clubService = ClubDaoService(application2)

    fun onCreate(){
    }

    fun onStart(){
        getClubs()
    }

    private fun getClubs(){
        val repo = LoginRepository(LoginDataSource(), application2)
        // repo.getUser()
        val user = repo.user
        if (user?.userId != null && user.userId != ""){
            viewModelScope.launch(Dispatchers.IO) {
                val response = ApiService().getClubsFromUser(user.userId!!, user.token!!)
                if (response != null){
                    clubService.deleteAll()
                    val listType: Type = object: TypeToken<List<ClubData>>() {}.type
                    val clubs = Gson().fromJson<List<ClubData>>(response, listType)

                    for (club in clubs){
                        val entity = ClubEntity(club.id, club.name, club.description, club.streetName, club.houseNumber, club.city, club.postalCode, club.country)

                        clubService.insert(entity)
                    }
                }
            }
        }
    }
}