package com.yuexun.myapplication.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity
data class HybridApp(
    @PrimaryKey
    val appKey: String,
    @ColumnInfo(name = "downloadUrl")
    val downloadUrl: String,
    @ColumnInfo(name = "h5DeployStatusEnum")
    val h5DeployStatusEnum: Int,
    @ColumnInfo(name = "hash")
    val hash: String,
    @ColumnInfo(name = "latestVersion")
    val latestVersion: String,
    @ColumnInfo(name = "dueFlag")
    val dueFlag: Int,
    @ColumnInfo(name = "state")
    val state: Int,
    @ColumnInfo(name = "appTypeEnum")
    val appTypeEnum: Int,
    @ColumnInfo(name = "messageCount")
    val messageCount: Int,
    @ColumnInfo(name = "tagId")
    val tagId: String,
    @ColumnInfo(name = "appNatureEnum")
    val appNatureEnum: Int,
    @ColumnInfo(name = "appHeaderColorEnum")
    val appHeaderColorEnum: Int,
    @ColumnInfo(name = "appLogoUuid")
    val appLogoUuid: String,
    @ColumnInfo(name = "appName")
    val appName: String,
    @ColumnInfo(name = "appId")
    val appId: String
)


@Entity
data class CommonApp(
    @PrimaryKey
    val appKey: String,
    @ColumnInfo(name = "downloadUrl")
    val downloadUrl: String,
    @ColumnInfo(name = "h5DeployStatusEnum")
    val h5DeployStatusEnum: Int,
    @ColumnInfo(name = "hash")
    val hash: String,
    @ColumnInfo(name = "latestVersion")
    val latestVersion: String,
    @ColumnInfo(name = "dueFlag")
    val dueFlag: Int,
    @ColumnInfo(name = "state")
    val state: Int,
    @ColumnInfo(name = "appTypeEnum")
    val appTypeEnum: Int,
    @ColumnInfo(name = "messageCount")
    val messageCount: Int,
    @ColumnInfo(name = "tagId")
    val tagId: String,
    @ColumnInfo(name = "appNatureEnum")
    val appNatureEnum: Int,
    @ColumnInfo(name = "appHeaderColorEnum")
    val appHeaderColorEnum: Int,
    @ColumnInfo(name = "appLogoUuid")
    val appLogoUuid: String,
    @ColumnInfo(name = "appName")
    val appName: String,
    @ColumnInfo(name = "appId")
    val appId: String
)

@Entity
data class ApiResponseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @Embedded
    val tagAppList: List<TagApp>,
    @Embedded
    val commonAppList: List<CommonApp>
)

@Entity
data class TagApp(
    @PrimaryKey
    val tagId: Int,
    val tagName: String,
) {
    @Ignore
    val hybridAppList: List<HybridApp> = emptyList()
}