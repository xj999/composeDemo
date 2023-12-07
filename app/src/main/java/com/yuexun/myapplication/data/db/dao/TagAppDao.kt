package com.yuexun.myapplication.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yuexun.myapplication.data.db.entity.TagApp
import kotlinx.coroutines.flow.Flow

@Dao
interface TagAppDao{
    @Query("SELECT * FROM TagApp")
    fun getAll(): Flow<List<TagApp>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg tag: TagApp)
    @Delete
    suspend fun delete(tag: TagApp)
}