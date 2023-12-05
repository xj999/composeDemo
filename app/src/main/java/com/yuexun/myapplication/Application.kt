package com.yuexun.myapplication

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree

class Application:Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(object : DebugTree() {
            override fun isLoggable(tag: String?, priority: Int): Boolean {
                return com.yuexun.myapplication.BuildConfig.DEBUG
            }
        })
    }
}