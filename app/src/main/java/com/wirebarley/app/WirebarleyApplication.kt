package com.wirebarley.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WirebarleyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}