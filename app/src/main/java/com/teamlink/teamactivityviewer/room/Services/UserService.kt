package com.teamlink.teamactivityviewer.room.Services

import android.app.Application
import androidx.lifecycle.LiveData
import com.teamlink.teamactivityviewer.room.entity.UserEntity
import com.teamlink.teamactivityviewer.services.DataProvider

class UserService(application: Application) {
    var userDb = DataProvider.getInstance(application).userDao()

    fun get(): UserEntity? {
        return userDb.getUser()
    }

    fun insert(vararg user: UserEntity){
        userDb.insertAll(*user)
    }

    fun insert(user: UserEntity){
        userDb.insert(user)
    }

    fun deleteAll(){
        userDb.deleteAll()
    }

    fun delete(user: UserEntity){
        userDb.delete(user)
    }
}