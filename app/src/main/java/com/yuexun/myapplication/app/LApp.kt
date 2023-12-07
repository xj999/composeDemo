package com.yuexun.myapplication.app

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.yuexun.myapplication.data.db.AppDatabase
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree


@HiltAndroidApp
class LApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(object : DebugTree() {
            override fun isLoggable(tag: String?, priority: Int): Boolean {
                return BuildConfigProcessor.DEBUG
            }
        })
    }

    @Target(AnnotationTarget.CLASS)
    annotation class BuildConfigProvider

    // 注解处理器的实现
    @BuildConfigProvider
    class BuildConfigProcessor {
        companion object {
            const val DEBUG = true
        }
    }

}
