package com.midai.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.midai.data.db.entity.TagApp
import com.midai.data.db.entity.TagWithHybridAppList
import kotlinx.coroutines.flow.Flow

@Dao
interface TagAppDao{
    @Query("SELECT * FROM TagApp")
    fun getAll(): Flow<List<TagApp>>

    @Upsert
    suspend fun upsert(vararg tag: TagApp)
    @Delete
    suspend fun delete(tag: TagApp)


    @Transaction
    @Query("SELECT * FROM TagApp")
    fun getTagWithHybridAppLists(): Flow<List<TagWithHybridAppList>>
}