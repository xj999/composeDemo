package com.yuexun.myapplication.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.yuexun.myapplication.data.db.entity.CommonApp
import kotlinx.coroutines.flow.Flow
import java.util.Arrays

@Dao
interface CommonAppDao {
    @Query("SELECT * FROM CommonApp")
    fun getAll(): Flow<List<CommonApp>>

    @Query("SELECT * FROM CommonApp WHERE appId IN (:appIds)")
    suspend fun loadAllByIds(appIds: List<String>): List<CommonApp>

    @Query("SELECT * FROM CommonApp WHERE appKey LIKE :appKey  LIMIT 1")
    suspend fun findByAppKey(appKey: String): CommonApp

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg commonApp: CommonApp)

    @Update
    suspend fun update(vararg commonApp: CommonApp)


    @Delete
    suspend fun delete(commonApp: CommonApp)

    @Delete
    suspend fun deleteAll(vararg commonApp: CommonApp)
}