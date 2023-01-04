package com.teamlink.teamactivityviewer.ui.personal

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.teamlink.teamactivityviewer.room.Services.ClubDaoService
import com.teamlink.teamactivityviewer.room.Services.EventService
import com.teamlink.teamactivityviewer.room.Services.UserService
import com.teamlink.teamactivityviewer.services.ApiService
import com.teamlink.teamactivityviewer.ui.data.LoginRepository
import com.teamlink.teamactivityviewer.ui.data.model.PersonalInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PersonalInfoViewModel(private val loginRepository: LoginRepository, application: Application): AndroidViewModel(application) {

    private val apiService = ApiService()
    val personalInfo: MutableLiveData<PersonalInfo?> = MutableLiveData<PersonalInfo?>()

    val clubDaoService: ClubDaoService = ClubDaoService(application)
    val eventService: EventService = EventService(application)
    val userService: UserService = UserService(application)

    @JvmName("getPersonalInfo1")
    fun getPersonalInfo(): PersonalInfo?{
        val user = loginRepository.getUser()
        if (user != null){
            val infoBody = apiService.getUserInfo(user.userId!!, user.token!!)
            if (infoBody != null){
                viewModelScope.launch(Dispatchers.Main) {
                    personalInfo.value = Gson().fromJson(infoBody, PersonalInfo::class.java)
                }
            }
        }

        return personalInfo.value
    }

    fun logout(){
        viewModelScope.launch(Dispatchers.IO){
            val user = loginRepository.getUser()
            if (user != null){
                apiService.logOut(user.userId)
                loginRepository.logout()
                eventService.deleteAll()
                clubDaoService.deleteAll()
                userService.deleteAll()
            }
        }

        personalInfo.value = null
    }
}