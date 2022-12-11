package com.teamlink.teamactivityviewer.room

import androidx.room.*
import com.teamlink.teamactivityviewer.room.entity.ClubEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClubDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg clubs: ClubEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(club: ClubEntity)

    @Delete
    fun delete(club: ClubEntity)

    @Query("DELETE FROM clubs")
    fun deleteAll()

    @Query("SELECT * FROM clubs")
    fun getAll(): Flow<List<ClubEntity>>

    @Query("SELECT * FROM clubs WHERE Id = :clubId")
    fun getById(clubId: String) : ClubEntity?
}