package com.teamlink.teamactivityviewer.services

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.teamlink.teamactivityviewer.room.ClubDao
import com.teamlink.teamactivityviewer.room.EventDao
import com.teamlink.teamactivityviewer.room.UserDao
import com.teamlink.teamactivityviewer.room.entity.ClubEntity
import com.teamlink.teamactivityviewer.room.entity.CustomPeriodEntity
import com.teamlink.teamactivityviewer.room.entity.EventEntity
import com.teamlink.teamactivityviewer.room.entity.UserEntity
import com.teamlink.teamactivityviewer.room.periodDao

@Database(entities = [ClubEntity::class, EventEntity::class, UserEntity::class, CustomPeriodEntity::class], version = 1)
abstract class DataProvider : RoomDatabase() {

    abstract fun clubDao(): ClubDao
    abstract fun eventDao(): EventDao
    abstract fun userDao(): UserDao
    abstract  fun periodDao(): periodDao

    companion object {

        private var instance: DataProvider? = null

        @Synchronized
        fun getInstance(application: Application): DataProvider {
            if (instance == null){
                instance = Room.databaseBuilder(application.baseContext.applicationContext, DataProvider::class.java, "clubs_database")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }

        private val test = object: Callback(){
        }
    }


}