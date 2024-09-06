package com.dika.moviecompose

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
//        Prefs = getSharedPreferences(PREFERENCE_NAME, 0)
    }
}