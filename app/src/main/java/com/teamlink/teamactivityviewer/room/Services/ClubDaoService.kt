package com.teamlink.teamactivityviewer.room.Services

import android.app.Application
import com.teamlink.teamactivityviewer.room.entity.ClubEntity
import com.teamlink.teamactivityviewer.services.DataProvider
import kotlinx.coroutines.flow.Flow

class ClubDaoService(application: Application) {

    var clubDb = DataProvider.getInstance(application).clubDao()

    fun all(): Flow<List<ClubEntity>>{
        return clubDb.getAll()
    }

    fun insert(vararg club: ClubEntity){
        clubDb.insertAll(*club)
    }

    fun insert(club: ClubEntity){
        clubDb.insert(club)
    }

    fun getById(clubId: String) : ClubEntity? {
        return clubDb.getById(clubId)
    }

    fun deleteAll(){
        clubDb.deleteAll()
    }
}