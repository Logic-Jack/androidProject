package com.teamlink.teamactivityviewer.ui.clubs

import androidx.lifecycle.ViewModel
import com.teamlink.teamactivityviewer.services.DataProvider
import com.teamlink.teamactivityviewer.ui.data.model.ClubData

class ClubDetailViewModel: ViewModel() {


    fun getClub(clubId: String) : ClubData? {
        var list = DataProvider.getInstance().clubs.value

        return list?.find { club -> club.Id == clubId }
    }
}