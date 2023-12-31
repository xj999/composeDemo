package com.midai.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.midai.data.db.dao.CommonAppDao
import com.midai.data.db.dao.HybridAppDao
import com.midai.data.db.dao.TagAppDao
import com.midai.data.db.entity.HybridApp
import com.midai.data.db.entity.TagApp

@Database(entities = [HybridApp::class, TagApp::class], version = 1,exportSchema=false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun commonAppDao(): CommonAppDao
    abstract fun hybridAppDao(): HybridAppDao
    abstract fun tagAppDao(): TagAppDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, employeeId: Long): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    employeeId.toString() + "_yxTeacher"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}