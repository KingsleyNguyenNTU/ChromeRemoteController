package com.mkhoi.chromeremotecontroller

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.mkhoi.chromeremotecontroller.database.PCEntity


class MainViewModel(private val repository: MainRepository): ViewModel() {
    val connectedPCs : LiveData<List<PCEntity>> = repository.getAllConnectedPCs()
    val selectedPC: MutableLiveData<PCEntity?> = MutableLiveData()

    fun updateSelectedPc(context: Context, pcName: String, selectedTokenId: String){
        repository.updateSelectedPCByTokenId(context, pcName, selectedTokenId, selectedPC)
    }

    class Factory : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(MyApp.component.mainRepository) as T
        }
    }
}