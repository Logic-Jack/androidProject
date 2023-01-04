package com.teamlink.teamactivityviewer.room

import androidx.room.*
import com.teamlink.teamactivityviewer.room.entity.CustomPeriodEntity

@Dao
interface periodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg periods: CustomPeriodEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(period: CustomPeriodEntity)

    @Query("Delete From periods")
    fun deleteAll()

    @Query("Select * FROM periods WHERE eventId = :eventId")
    fun getByEventId(eventId: String) : List<CustomPeriodEntity>
}