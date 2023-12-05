package com.yuexun.myapplication.network

import com.yuexun.myapplication.db.entity.ApiResponse
import retrofit2.http.POST

interface YxService {
    @POST("store/api/v2.0/inquireClassifiedPluginListForTeacherAccount.json")
    suspend fun inquireClassifiedPluginListForTeacherAccount(): ApiResponse
}