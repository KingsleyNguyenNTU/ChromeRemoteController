package com.mkhoi.chromeremotecontroller.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [PCEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pcDao(): PCDao

    companion object {
        const val DB_NAME = "ChromeRemoteController"
    }
}