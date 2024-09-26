package com.github.smalljooj.sunflowerapp

import android.app.Application
import com.github.smalljooj.sunflowerapp.data.AppContainer
import com.github.smalljooj.sunflowerapp.data.DefaultAppContainer

class SunflowerApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}