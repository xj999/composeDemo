package com.yuexun.myapplication.db.entity

data class HybridApp(
    val downloadUrl: String,
    val h5DeployStatusEnum: Int,
    val hash: String,
    val latestVersion: String,
    val dueFlag: Int,
    val state: Int,
    val appTypeEnum: Int,
    val messageCount: Int,
    val tagId: String,
    val appNatureEnum: Int,
    val appKey: String,
    val appHeaderColorEnum: Int,
    val appLogoUuid: String,
    val appName: String,
    val appId: String
)

data class TagApp(
    val hybridAppList: List<HybridApp>,
    val tagName: String,
    val tagId: String
)

data class CommonApp(
    val downloadUrl: String,
    val h5DeployStatusEnum: Int,
    val hash: String,
    val latestVersion: String,
    val dueFlag: Int,
    val state: Int,
    val appTypeEnum: Int,
    val messageCount: Int,
    val tagId: String,
    val appNatureEnum: Int,
    val appKey: String,
    val appHeaderColorEnum: Int,
    val appLogoUuid: String,
    val appName: String,
    val appId: String
)

data class ApiResponse(
    val tagAppList: List<TagApp>,
    val commonAppList: List<CommonApp>
)
