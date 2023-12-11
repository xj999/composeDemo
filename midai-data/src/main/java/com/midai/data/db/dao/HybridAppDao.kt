package com.midai.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.midai.data.db.entity.HybridApp
import kotlinx.coroutines.flow.Flow

@Dao
interface HybridAppDao {
    @Query("SELECT * FROM HybridApp where type = 'HybridApp'")
    fun getAll(): Flow<List<HybridApp>>

    @Query("SELECT * FROM HybridApp WHERE appId IN (:appIds)")
    suspend fun loadAllByIds(appIds: List<String>): List<HybridApp>

    @Query("SELECT * FROM HybridApp WHERE appKey LIKE :appKey  LIMIT 1")
    suspend fun findByAppKey(appKey: String): HybridApp

    @Upsert
    suspend fun upsert(vararg hybridApp: HybridApp)

    @Delete
    suspend fun delete(hybridApp: HybridApp)
}