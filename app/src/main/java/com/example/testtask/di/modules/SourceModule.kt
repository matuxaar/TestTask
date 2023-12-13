package com.example.testtask.di.modules

import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.location.LocationManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides

@Module
class SourceModule {

    @Provides
    fun providePrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_KEY, MODE_PRIVATE)
    }

    @Provides
    fun provideLocation(context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }


    companion object {
        private const val PREFS_KEY = "prefs_key"
    }
}