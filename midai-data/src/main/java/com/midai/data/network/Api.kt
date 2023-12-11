package com.midai.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import timber.log.Timber

object Api {
    private val apiService: YxService
    private var retrofit: Retrofit
    private const val TIME_OUT = 15
    private fun getServerHost(): String {
        return "https://st.yuexunit.com"

    }


    init {
        val logging = HttpLoggingInterceptor { message -> Timber.i(message) }

        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS).addInterceptor(logging).build()
        retrofit = Retrofit.Builder().baseUrl(getServerHost()).client(client).addConverterFactory(
            YxGsonConverterFactory.create()
        ).build()
        apiService = retrofit.create(YxService::class.java)
    }

    suspend fun inquireClassifiedPluginListForTeacherAccount(): com.midai.data.db.entity.ApiResponseEntity =
        withContext(Dispatchers.IO) {
            return@withContext apiService.inquireClassifiedPluginListForTeacherAccount("YX_TEACHER_MOBILE", "470d0a1f5f504754afe628763a54a5ce")
        }
}