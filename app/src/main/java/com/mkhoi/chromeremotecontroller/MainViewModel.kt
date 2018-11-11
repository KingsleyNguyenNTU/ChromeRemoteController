package com.mkhoi.chromeremotecontroller

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.mkhoi.chromeremotecontroller.database.PCEntity


class MainViewModel(private val repository: MainRepository): ViewModel() {
    val connectedPCs : LiveData<List<PCEntity>> = repository.getAllConnectedPCs()
    val selectedPC: MutableLiveData<PCEntity?> = MutableLiveData()

    class Factory : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(MyApp.component.mainRepository) as T
        }
    }
}