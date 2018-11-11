package com.mkhoi.chromeremotecontroller.dagger

import android.arch.persistence.room.Room
import com.mkhoi.chromeremotecontroller.MyApp
import com.mkhoi.chromeremotecontroller.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: MyApp) {
    @Provides
    @Singleton
    fun provideApp() = app

    @Provides
    @Singleton
    fun providePCDao(appDatabase: AppDatabase) =  appDatabase.pcDao()

    @Provides
    @Singleton
    fun provideDatabase() =  Room.databaseBuilder(app, AppDatabase::class.java, AppDatabase.DB_NAME)
            .build()
}