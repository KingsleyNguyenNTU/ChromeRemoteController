package com.mkhoi.chromeremotecontroller

import android.arch.lifecycle.LiveData
import com.mkhoi.chromeremotecontroller.database.PCDao
import com.mkhoi.chromeremotecontroller.database.PCEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository@Inject constructor(private val pcDao: PCDao) {
    fun getAllConnectedPCs(): LiveData<List<PCEntity>> {
        return pcDao.getAll()
    }

}