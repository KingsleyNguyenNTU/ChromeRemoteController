package com.mkhoi.chromeremotecontroller

import android.app.Activity
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.widget.Toast
import com.mkhoi.chromeremotecontroller.database.BackgroundAsyncTask
import com.mkhoi.chromeremotecontroller.database.PCDao
import com.mkhoi.chromeremotecontroller.database.PCEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository@Inject constructor(private val pcDao: PCDao) {
    fun getAllConnectedPCs(): LiveData<List<PCEntity>> {
        return pcDao.getAll()
    }

    fun updateSelectedPCByTokenId(context: Context, pcName: String, tokenId: String, result: MutableLiveData<PCEntity?>){
        BackgroundAsyncTask().execute({
            var selectedPc = pcDao.getPCByToken(tokenId)
            if (selectedPc == null) {
                selectedPc = PCEntity(name = pcName, tokenId = tokenId)
                pcDao.insert(selectedPc)
            } else {
                (context as Activity).runOnUiThread {
                    Toast.makeText(context,
                            "The device has been added previously as ${selectedPc.name}",
                            Toast.LENGTH_LONG).show()
                }
            }
            result.postValue(selectedPc)
        })
    }
}