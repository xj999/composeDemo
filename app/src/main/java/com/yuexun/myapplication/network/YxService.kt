package com.yuexun.myapplication.network

import com.yuexun.myapplication.data.db.entity.ApiResponseEntity
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface YxService {
    @FormUrlEncoded
    @POST("store/api/v2.0/inquireClassifiedPluginListForTeacherAccount.json")
    suspend fun inquireClassifiedPluginListForTeacherAccount(
        @Field("appKey") appKey: String,
        @Field("sessionUuid") sessionUuid: String
    ): ApiResponseEntity
}