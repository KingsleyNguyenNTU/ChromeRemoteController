package com.mkhoi.chromeremotecontroller

import android.app.Application
import com.mkhoi.chromeremotecontroller.dagger.AppModule
import com.mkhoi.chromeremotecontroller.dagger.DaggerMyAppComponent
import com.mkhoi.chromeremotecontroller.dagger.MyAppComponent


class MyApp: Application() {

    companion object {
        lateinit var component: MyAppComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerMyAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
        component.inject(this)
    }
}