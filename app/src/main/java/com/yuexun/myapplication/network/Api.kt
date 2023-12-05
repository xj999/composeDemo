package com.yuexun.myapplication.network

import com.yuexun.myapplication.db.entity.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import timber.log.Timber

object Api {
    private val apiService:YxService
    private var retrofit: Retrofit
    private const val TIME_OUT = 4
    private fun getServerHost(): String {
        return "https://st.yuexunit.com"

    }


    init {
        val logging = HttpLoggingInterceptor { message -> Timber.i(message) }

        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .connectTimeout(TIME_OUT.toLong(), TimeUnit.MINUTES).build()
        retrofit = Retrofit.Builder().baseUrl(getServerHost()).client(client).build()
        apiService = retrofit.create(YxService::class.java)
    }

    suspend fun inquireClassifiedPluginListForTeacherAccount(): ApiResponse =
        withContext(Dispatchers.IO) {
            return@withContext apiService.inquireClassifiedPluginListForTeacherAccount()
        }
}