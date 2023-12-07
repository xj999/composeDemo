package com.yuexun.myapplication.di

import com.yuexun.myapplication.data.HybridAppRepository
import com.yuexun.myapplication.data.HybridAppRepositoryImpl
import com.yuexun.myapplication.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTaskRepository(appDatabase: AppDatabase): HybridAppRepository {
        return HybridAppRepositoryImpl(appDatabase)
    }
}
