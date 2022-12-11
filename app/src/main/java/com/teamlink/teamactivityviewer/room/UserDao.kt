package com.teamlink.teamactivityviewer.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.teamlink.teamactivityviewer.room.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserEntity)

    @Delete
    fun delete(user: UserEntity)

    @Query("DELETE FROM user")
    fun deleteAll()

    @Query("SELECT * FROM user LIMIT 1")
    fun getUser(): UserEntity?
}