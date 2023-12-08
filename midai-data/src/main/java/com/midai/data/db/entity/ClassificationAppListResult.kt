package com.midai.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.Relation


@Entity
data class HybridApp(
    @PrimaryKey
    val appKey: String,
    val downloadUrl: String,
    val h5DeployStatusEnum: Int,
    val hash: String,
    val latestVersion: String,
    val dueFlag: Int,
    val state: Int,
    val appTypeEnum: Int,
    val messageCount: Int,
    val tagId: Int,
    val appNatureEnum: Int,
    val appHeaderColorEnum: Int,
    val appLogoUuid: String,
    val appName: String,
    val appId: String
)


@Entity
data class CommonApp(
    @PrimaryKey
    val appKey: String,
    val downloadUrl: String,
    val h5DeployStatusEnum: Int,
    val hash: String,
    val latestVersion: String,
    val dueFlag: Int,
    val state: Int,
    val appTypeEnum: Int,
    val messageCount: Int,
    val tagId: Int,
    val appNatureEnum: Int,
    val appHeaderColorEnum: Int,
    val appLogoUuid: String,
    val appName: String,
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


data class TagWithHybridAppList(
    @Embedded val tag: TagApp,
    @Relation(
        parentColumn = "tagId",
        entityColumn = "tagId"
    )
    val hybridAppList: List<HybridApp>
)