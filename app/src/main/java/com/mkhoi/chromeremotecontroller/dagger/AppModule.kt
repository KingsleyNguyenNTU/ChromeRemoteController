package com.mkhoi.chromeremotecontroller.dagger

import com.mkhoi.chromeremotecontroller.MyApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: MyApp) {
    @Provides
    @Singleton
    fun provideApp() = app
}