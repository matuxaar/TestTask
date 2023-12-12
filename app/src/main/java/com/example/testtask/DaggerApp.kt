package com.example.testtask

import android.app.Application
import com.example.testtask.di.AppComponent
import com.example.testtask.di.DaggerAppComponent

class DaggerApp : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}