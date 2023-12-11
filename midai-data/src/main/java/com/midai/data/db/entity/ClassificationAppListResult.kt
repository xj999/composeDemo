package com.midai.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.Relation

enum class AppType {
    MYAPP, HybridApp
}


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
    val appId: String,
    val appName: String,
    val appLogoUuid: String,
    val type:AppType
)


@Entity
data class ApiResponseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @Embedded
    val tagAppList: List<TagApp>,
    @Embedded
    val commonAppList: List<HybridApp>
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

fun TagApp.toTagWithHybridAppList(hybridAppList: List<HybridApp>): TagWithHybridAppList {
    return TagWithHybridAppList(this, hybridAppList)
}