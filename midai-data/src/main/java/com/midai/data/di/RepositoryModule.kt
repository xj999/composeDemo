package com.midai.data.di

import com.midai.data.HybridAppRepository
import com.midai.data.HybridAppRepositoryImpl
import com.midai.data.db.AppDatabase
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
    fun provideTaskRepository(appDatabase: com.midai.data.db.AppDatabase): com.midai.data.HybridAppRepository {
        return com.midai.data.HybridAppRepositoryImpl(appDatabase)
    }
}
