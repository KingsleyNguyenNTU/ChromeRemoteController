package com.mkhoi.chromeremotecontroller.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface PCDao {
    @Insert
    fun insert(entity: PCEntity): Long

    @Update
    fun update(entity: PCEntity)

    @Delete
    fun delete(entity: PCEntity)

    @Query("select * from PCEntity")
    fun getAll(): LiveData<List<PCEntity>>

    @Query("select * from PCEntity where tokenId = :tokenId")
    fun getPCByToken(tokenId: String): PCEntity?
}