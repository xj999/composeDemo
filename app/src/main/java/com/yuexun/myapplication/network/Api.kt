package com.yuexun.myapplication.network

import com.yuexun.myapplication.data.db.entity.ApiResponseEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import timber.log.Timber

object Api {
    private val apiService: YxService
    private var retrofit: Retrofit
    private const val TIME_OUT = 4
    private fun getServerHost(): String {
        return "https://st.yuexunit.com"

    }


    init {
        val logging = HttpLoggingInterceptor { message -> Timber.i(message) }

        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .connectTimeout(TIME_OUT.toLong(), TimeUnit.MINUTES).addInterceptor(logging).build()
        retrofit = Retrofit.Builder().baseUrl(getServerHost()).client(client).addConverterFactory(YxGsonConverterFactory.create()).build()
        apiService = retrofit.create(YxService::class.java)
    }

    suspend fun inquireClassifiedPluginListForTeacherAccount(): ApiResponseEntity =
        withContext(Dispatchers.IO) {
            return@withContext apiService.inquireClassifiedPluginListForTeacherAccount("YX_TEACHER_MOBILE", "b8cc1422d2404208b301e0577f14fc48")
        }
}