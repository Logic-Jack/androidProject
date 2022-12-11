package com.teamlink.teamactivityviewer.room

import androidx.room.*
import com.teamlink.teamactivityviewer.room.entity.CustomPeriodEntity
import com.teamlink.teamactivityviewer.room.entity.EventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg events: EventEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(events: EventEntity)

    @Delete
    fun delete(event: EventEntity)

    @Query("Delete From events")
    fun deleteAll()

    @Query("SELECT * FROM events")
    fun getAll(): Flow<List<EventEntity>>

    @Query("SELECT * FROM events WHERE Id = :eventId")
    fun getById(eventId: String) : EventEntity?

    @Query("SELECT * FROM events " +
            "JOIN periods p on p.EventId = events.Id " +
            "WHERE events.Id = :eventId")
    fun getAllPeriods(eventId: String) : Flow<List<CustomPeriodEntity>>
}