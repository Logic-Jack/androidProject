package com.teamlink.teamactivityviewer.ui.clubs

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.teamlink.teamactivityviewer.room.Services.ClubDaoService
import com.teamlink.teamactivityviewer.room.entity.ClubEntity
import com.teamlink.teamactivityviewer.services.ApiService
import com.teamlink.teamactivityviewer.services.DataProvider
import com.teamlink.teamactivityviewer.ui.data.model.ClubData
import java.io.InputStream

class ClubDetailViewModel(application: Application): AndroidViewModel(application) {

    val clubService = ClubDaoService(application)
    var apiService: ApiService = ApiService()

    fun getrandomImage(): Bitmap? {
        try {
            val response = apiService.getRandomImage()
            if (response != null){
                return BitmapFactory.decodeByteArray(response, 0, response.size)
            }
        }catch (ex: Exception){
            println("oeps")
        }

        return null
    }


    fun getClub(clubId: String) : ClubEntity? {
        return clubService.getById(clubId)
    }
}