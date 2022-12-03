package com.teamlink.teamactivityviewer.ui.clubs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.teamlink.teamactivityviewer.services.ApiService
import com.teamlink.teamactivityviewer.services.DataProvider
import com.teamlink.teamactivityviewer.ui.data.LoginDataSource
import com.teamlink.teamactivityviewer.ui.data.LoginRepository
import com.teamlink.teamactivityviewer.ui.data.model.ClubData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.reflect.Type

class ClubListViewModel : ViewModel() {

    private val dataProvider: DataProvider = DataProvider.getInstance()

    fun onCreate(){
        //DataProvider.getInstance().clubs.value = getClubs()
    }

    fun onStart(){
        DataProvider.getInstance().clubs.value = getClubs()
    }

    fun onViewVisible(){
        DataProvider.getInstance().clubs.value = getClubs()
    }

    private fun getClubs(): List<ClubData>{
        val user = LoginRepository(LoginDataSource()).user
        if (user?.userId != null && user.userId != ""){
            viewModelScope.launch(Dispatchers.IO) {
                val response = ApiService().getClubsFromUser(user.userId!!)
                if (response != null){
                    viewModelScope.launch(Dispatchers.Main) {
                        val listType: Type = object: TypeToken<List<ClubData>>() {}.type
                        DataProvider.getInstance().clubs.value = Gson().fromJson<List<ClubData>>(response, listType)
                    }
                }
            }
        }

        return emptyList()
    }
}