package com.mkhoi.chromeremotecontroller.dagger

import com.mkhoi.chromeremotecontroller.MainRepository
import com.mkhoi.chromeremotecontroller.MyApp
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface MyAppComponent {
    fun inject(app: MyApp)

    val mainRepository: MainRepository
}