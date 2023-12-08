package com.midai.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.midai.data.db.entity.CommonApp
import kotlinx.coroutines.flow.Flow

@Dao
interface CommonAppDao {
    @Query("SELECT * FROM CommonApp")
    fun getAll(): Flow<List<CommonApp>>

    @Query("SELECT * FROM CommonApp WHERE appId IN (:appIds)")
    suspend fun loadAllByIds(appIds: List<String>): List<CommonApp>

    @Query("SELECT * FROM CommonApp WHERE appKey LIKE :appKey  LIMIT 1")
    suspend fun findByAppKey(appKey: String): CommonApp

    @Upsert
    suspend fun upsert(vararg commonApp: CommonApp)

    @Update
    suspend fun update(vararg commonApp: CommonApp)


    @Delete
    suspend fun delete(commonApp: CommonApp)

    @Delete
    suspend fun deleteAll(vararg commonApp: CommonApp)
}