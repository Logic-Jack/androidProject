package com.teamlink.teamactivityviewer.ui.clubs

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.teamlink.teamactivityviewer.room.Services.ClubDaoService
import com.teamlink.teamactivityviewer.room.entity.ClubEntity
import com.teamlink.teamactivityviewer.services.DataProvider
import com.teamlink.teamactivityviewer.ui.data.model.ClubData

class ClubDetailViewModel(application: Application): AndroidViewModel(application) {

    val clubService = ClubDaoService(application)

    fun getClub(clubId: String) : ClubEntity? {
        return clubService.getById(clubId)
    }
}