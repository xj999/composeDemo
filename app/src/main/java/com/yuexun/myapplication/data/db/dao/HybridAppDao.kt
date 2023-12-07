package com.yuexun.myapplication.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yuexun.myapplication.data.db.entity.HybridApp
import kotlinx.coroutines.flow.Flow
import java.util.Arrays

@Dao
interface HybridAppDao {
    @Query("SELECT * FROM HybridApp")
    fun getAll(): Flow<List<HybridApp>>

    @Query("SELECT * FROM HybridApp WHERE appId IN (:appIds)")
    suspend fun loadAllByIds(appIds: List<String>): List<HybridApp>

    @Query("SELECT * FROM HybridApp WHERE appKey LIKE :appKey  LIMIT 1")
    suspend fun findByAppKey(appKey: String): HybridApp

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg hybridApp: HybridApp)

    @Delete
    suspend fun delete(hybridApp: HybridApp)
}