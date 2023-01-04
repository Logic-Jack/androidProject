package com.teamlink.teamactivityviewer.ui.clubs

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.teamlink.teamactivityviewer.room.Services.ClubDaoService
import com.teamlink.teamactivityviewer.room.entity.ClubEntity
import com.teamlink.teamactivityviewer.services.ApiService
import com.teamlink.teamactivityviewer.ui.data.model.ClubData
import com.teamlink.teamactivityviewer.ui.data.model.LoggedInUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.reflect.Type

class ClubListViewModel(application: Application) : AndroidViewModel(application) {

    private val application2 = application

    var clubService = ClubDaoService(application2)
    val clubs: MutableLiveData<List<ClubEntity>> = MutableLiveData<List<ClubEntity>>()
    fun onStart(user: LoggedInUser){
        getClubs(user)
    }

    private fun getClubs(user: LoggedInUser){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = ApiService().getClubsFromUser(user.userId!!, user.token!!)
                if (response != null){
                    clubService.deleteAll()
                    val listType: Type = object: TypeToken<List<ClubData>>() {}.type
                    val clubsData = Gson().fromJson<List<ClubData>>(response, listType)
                    val entities = clubsData.map { club ->
                        ClubEntity(club.id, club.name, club.description, club.streetName, club.houseNumber, club.city, club.postalCode, club.country)
                    }

                    clubService.insert(*entities.toTypedArray())
//                    for (club in clubsData){
//                        val entity = ClubEntity(club.id, club.name, club.description, club.streetName, club.houseNumber, club.city, club.postalCode, club.country)
//
//                        clubService.insert(entity)
//                    }
                    viewModelScope.launch(Dispatchers.Main){
                        clubs.value = entities
                    }
                }
            }catch (ex: Exception){
                showMessage("retrieving clubs failed")
            }
        }
    }

    private fun showMessage(messageString: String) {
        viewModelScope.launch(Dispatchers.Main) {
            Toast.makeText(application2.baseContext, messageString, Toast.LENGTH_LONG).show()
        }
    }
}